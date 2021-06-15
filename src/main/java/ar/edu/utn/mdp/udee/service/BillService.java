package ar.edu.utn.mdp.udee.service;

import ar.edu.utn.mdp.udee.model.Bill;
import ar.edu.utn.mdp.udee.model.dto.bill.BillDTO;
import ar.edu.utn.mdp.udee.model.response.PaginationResponse;
import ar.edu.utn.mdp.udee.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final ConversionService conversionService;

    @Autowired
    public BillService(BillRepository billRepository, ConversionService conversionService) {
        this.billRepository = billRepository;
        this.conversionService = conversionService;
    }

    public PaginationResponse<BillDTO> getAll(Pageable pageable, Date since, Date until) {
        Page<Bill> billPage = billRepository.findByRange(since, until, pageable);
        Page<BillDTO> billDTOPage = billPage.map(bill -> conversionService.convert(bill, BillDTO.class));
        return new PaginationResponse<>(billDTOPage.getContent(), billDTOPage.getTotalPages(), billDTOPage.getTotalElements());
    }

    public PaginationResponse<BillDTO> getUnpaidByAddress(Pageable pageable, Integer addressId) {
        Page<Bill> billPage = billRepository.findUnpaidByAddress(addressId, pageable);
        Page<BillDTO> billDTOPage = billPage.map(bill -> conversionService.convert(bill, BillDTO.class));
        return new PaginationResponse<>(billDTOPage.getContent(), billDTOPage.getTotalPages(), billDTOPage.getTotalElements());
    }

    public PaginationResponse<BillDTO> getUnpaidByClient(Pageable pageable, Integer clientId) {
        Page<Bill> billPage = billRepository.findUnpaidByClient(clientId, pageable);
        Page<BillDTO> billDTOPage = billPage.map(bill -> conversionService.convert(bill, BillDTO.class));
        return new PaginationResponse<>(billDTOPage.getContent(), billDTOPage.getTotalPages(), billDTOPage.getTotalElements());
    }
}
