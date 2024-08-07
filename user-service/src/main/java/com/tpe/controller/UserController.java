package com.tpe.controller;


import com.tpe.payload.request.SigninRequest;
import com.tpe.payload.request.UserRequestForCreateOrUpdate;
import com.tpe.payload.request.UserRequestForRegister;
import com.tpe.payload.request.UserRequestForUpdatePassword;
import com.tpe.payload.response.SigninResponse;
import com.tpe.payload.response.UserResponse;
import com.tpe.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // http://localhost:8097/users/signin + POST
    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signIn(@RequestBody @Valid SigninRequest signInRequest) {
        return userService.authenticateUser(signInRequest);
    }


    // http://localhost:8097/users/register + POST
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequestForRegister userRequestForRegister) {
        return userService.register(userRequestForRegister);
    }

    // http://localhost:8097/users + POST
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public ResponseEntity<UserResponse> getAuthenticatedUser(HttpServletRequest httpServletRequest) {
        return userService.getAuthenticatedUser(httpServletRequest);
    }

    // http://localhost:8097/users/loans + POST
   /* @PostMapping("/loans")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public ResponseEntity<Page<LoanResponse>> getAllLoansByUserByPage(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        return userService.getAllLoansByUserByPage(httpServletRequest, page, size, sort, type);
    }
*/
    // http://localhost:8097/users + Get
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        return userService.getAllUsersByPage(page, size, sort, type);
    }

    // http://localhost:8097/users/{userId} + GET
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    // http://localhost:8097/users/{userId} + DELETE
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable Long userId) {
        return userService.deleteUserById(userId);
    }

    // http://localhost:8097/users/{userRole} + POST
    @PostMapping("/{userRole}") //Dökümantasyona göre kıyasla
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequestForCreateOrUpdate userRequestForCreateOrUpdate,
                                                   HttpServletRequest httpServletRequest,
                                                   @PathVariable String userRole) {
        return userService.createUser(userRequestForCreateOrUpdate, httpServletRequest, userRole);
    }

    // http://localhost:8097/users/{userId} + PUT
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequestForCreateOrUpdate userRequestForCreateOrUpdate, //farklı dto düşünülebilir create-->update
                                                   @PathVariable Long userId,
                                                   HttpServletRequest httpServletRequest) {

        return userService.updateUser(userRequestForCreateOrUpdate, userId, httpServletRequest);

    }

    // http://localhost:8097/users/{userIdForPassword} + PUT
    @PutMapping("/{userIdForPassword}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public ResponseEntity<String> updateUserPassword(@RequestBody @Valid UserRequestForUpdatePassword userRequestForUpdatePassword,
                                                           @PathVariable Long userId,
                                                           HttpServletRequest httpServletRequest) {

        return userService.updateUserPassword(userRequestForUpdatePassword, userId, httpServletRequest);

    }

}
