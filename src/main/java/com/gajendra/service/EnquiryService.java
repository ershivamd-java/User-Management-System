package com.gajendra.service;

import com.gajendra.entity.Enquiry;

import java.util.List;

public interface EnquiryService {

    Enquiry createEnquiry(Enquiry enquiry);

    List<Enquiry> getAllEnquiries();

    Enquiry getEnquiryById(Long id);

    Enquiry updateEnquiry(Long id, Enquiry enquiry);

    void deleteEnquiry(Long id);
}
