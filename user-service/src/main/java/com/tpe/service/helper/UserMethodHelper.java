package com.tpe.service.helper;

import com.tpe.domain.User;
import com.tpe.enums.RoleType;
import com.tpe.exceptions.BadRequestException;
import com.tpe.exceptions.ResourceNotFoundException;
import com.tpe.payload.messages.ErrorMessages;
import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class UserMethodHelper {
    private final UserRepository userRepository;
    //private final LoanRepository loanRepository;


    @Transactional
    public User isUserExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,
                        userId)));
    }


    public boolean isAdmin(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getAttribute("name")!="Admin") {
            throw new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_ADMIN));
        }
        return true;
    }


    public void checkBuiltIn(User user) {
        if (Boolean.TRUE.equals(user.getBuiltIn())) {
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }

    //!!! isUserExistWithUsername
    public User isUserExistByEmail(String username) {

        User user = userRepository.findByEmail(username);
        if (user.getId() == null) {
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_USER_MESSAGE);
        }

        return user;

    }
    //!!! Check the role
    public void checkRole(User user, RoleType roleType){
        if (!user.getRoles().equals(roleType)) {
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.ROLE_NOT_FOUND, user.getId(),roleType));
        }
    }
    /*
    public List<Loan> getAllLoans(){

        return loanRepository.findAll();
    }*/

}