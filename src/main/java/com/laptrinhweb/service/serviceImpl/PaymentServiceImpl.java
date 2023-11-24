package com.laptrinhweb.service.serviceImpl;

import com.laptrinhweb.dto.HotelDto;
import com.laptrinhweb.dto.PaymentMethodDto;
import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.entity.PaymentAccount;
import com.laptrinhweb.repository.PaymentAccountRepository;
import com.laptrinhweb.repository.UserRepository;
import com.laptrinhweb.service.IPaymentServive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Lazy
@AllArgsConstructor
public class PaymentServiceImpl implements IPaymentServive {
    private final PaymentAccountRepository paymentAccountRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;



    @Override
    public ResponseEntity<?> createPaymentMethod(PaymentMethodDto dto) {
        var userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        var paymentAcc= modelMapper.map(dto, PaymentAccount.class);
        paymentAcc.setCVV(dto.getNumberCVV());

        if(!validatePayment(paymentAcc)){
            return ResponseEntity.status(400)
                    .body(ResponseData.builder()
                            .message("Input data incorrect. Dữ liệu truyền vào bị sai rồi." +
                                    "PaymentMethod : " +
                                    "    Credit_Card, PayPal, Momo, Other && " +
                                    "              Objects.nonNull(PaymentMethod) &&" +
                                    "                Objects.nonNull(CardName)  &&" +
                                    "                Objects.nonNull(CVV) &&"+ dto.toString())
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
                    );
        }
        var user=userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        paymentAcc.setUser(user);
        paymentAccountRepository.save(paymentAcc);
        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .build()
        );
    }

    private boolean validatePayment(PaymentAccount dto) {
        return Objects.nonNull(dto.getPaymentMethod()) &&
                Objects.nonNull(dto.getCardName())  &&
                Objects.nonNull(dto.getCVV()) ;

    }

    @Override
    public ResponseEntity<?> getAll() {
        var userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<PaymentAccount> paymentAccounts=paymentAccountRepository.findByUserEmail(userDetails.getUsername());

        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .data(paymentAccounts.stream()
                                .map(paymentAccount -> {
                                    var pym=modelMapper.map(paymentAccount, PaymentMethodDto.class);
                                    pym.setNumberCVV(paymentAccount.getCVV());
                                    return pym;
                                })
                                .toList())
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> deletePayment(Long paymentId) {
        var userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if(!paymentAccountRepository.existsById(paymentId)){
            return ResponseEntity.status(400)
                    .body(ResponseData.builder()
                            .message("Payment Id is incorrect. Khong tim thay cai nao co id nhu vay")
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
                    );
        }
        PaymentAccount paymentAccount=paymentAccountRepository.findById(paymentId).orElseThrow();
        if(!paymentAccount.getUser().getEmail().equals(userDetails.getUsername())){
            return ResponseEntity.status(403)
                    .body(ResponseData.builder()
                            .message("The user making the request does not have the necessary permissions to access the requested resource.\n. Vua thoi cha, ong hong co quyen xoa cai ni. Di xoa cai cua ong di. ")
                            .status(HttpStatus.FORBIDDEN)
                            .build()
                    );
        }
        paymentAccountRepository.delete(paymentAccount);
        return ResponseEntity.ok(
                ResponseData.builder()
                        .status(HttpStatus.OK)
                        .message("Success")
                        .build()
        );
    }
}
