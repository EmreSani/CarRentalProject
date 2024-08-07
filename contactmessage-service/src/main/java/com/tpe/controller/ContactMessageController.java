package com.tpe.controller;

import com.tpe.payload.ContactMessageRequest;
import com.tpe.payload.ContactMessageResponse;
import com.tpe.entity.ContactMessage;
import com.tpe.payload.bussiness.ResponseMessage;
import com.tpe.service.ContactMessageService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/save") // http://localhost:8080/contactMessages/save + POST + JSON
    public ResponseMessage<ContactMessageResponse> save(@Valid @RequestBody ContactMessageRequest contactMessageRequest){
        return  contactMessageService.save(contactMessageRequest);
    }

    @GetMapping("/getAll") // http://localhost:8080/contactMessages/getAll + GET
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<ContactMessageResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "dateTime") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){
        return contactMessageService.getAll(page,size,sort,type);
    }

    @GetMapping("/searchByEmail")  // http://localhost:8080/contactMessages/searchByEmail?email=aaa@bbb.com  + GET
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<ContactMessageResponse> searchByEmail(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "dateTime") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return contactMessageService.searchByEmail(email,page,size,sort,type);
    }

    // Not: Odev : searchBySubject *******************************************
    @GetMapping("/searchBySubject")// http://localhost:8080/contactMessages/searchBySubject?subject=deneme
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<ContactMessageResponse> searchBySubject(
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "dateTime") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type){
        return contactMessageService.searchBySubject(subject,page,size,sort,type);
    }

    @DeleteMapping("/deleteById/{contactMessageId}") // http://localhost:8080/contactMessages/deleteById/2 + DELETE
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteByIdPath(@PathVariable Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
    }

    @DeleteMapping("/deleteByIdParam")  //http://localhost:8080/contactMessages/deleteByIdParam?contactMessageId=2
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteById(@RequestParam(value = "contactMessageId") Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId)); // servisdeki ayni metod
    }

    @GetMapping("/searchBetweenDates")  //http://localhost:8080/contactMessages/searchBetweenDates?beginDate=2023-09-13&endDate=2023-09-15   + GET
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<ContactMessage>> searchBetweenDates(
            @RequestParam(value = "beginDate") String beginDateString,
            @RequestParam(value = "endDate") String endDateString
    ){
        List<ContactMessage> contactMessages = contactMessageService.searchBetweenDates(beginDateString, endDateString);
        return ResponseEntity.ok(contactMessages);
    }

    @GetMapping("/searchBetweenTimes")//http://localhost:8080/contactMessages/searchBetweenTimes?startHour=09&startMinute=00&endHour=17&endMinute=30  + GET
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<ContactMessage>> searchBetweenTimes(
            @RequestParam(value = "startHour") String startHourString,
            @RequestParam(value = "startMinute") String startMinuteString,
            @RequestParam(value = "endHour") String endHourString,
            @RequestParam(value = "endMinute") String endMinuteString

    ){
        List<ContactMessage> contactMessages = contactMessageService.searchBetweenTimes(startHourString,startMinuteString,endHourString,endMinuteString);
        return ResponseEntity.ok(contactMessages);
    }

    @GetMapping("/getByIdParam")//http://localhost:8080/contactMessages/getByIdParam?contactMessageId=1  + GET
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ContactMessage> getById(@RequestParam(value = "contactMessageId") Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.getContactMessageById(contactMessageId));
    }

    @GetMapping("/getById/{contactMessageId}")//http://localhost:8080/contactMessages/getById/1  + GET
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ContactMessage> getByIdPath(@PathVariable Long contactMessageId) {
        return ResponseEntity.ok(contactMessageService.getContactMessageById(contactMessageId));
    }


}
