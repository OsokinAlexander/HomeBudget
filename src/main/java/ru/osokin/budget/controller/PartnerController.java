package ru.osokin.budget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.osokin.budget.entity.Operation;
import ru.osokin.budget.entity.Partner;
import ru.osokin.budget.entity.PartnerDTO;
import ru.osokin.budget.repository.PartnerRepository;
import ru.osokin.budget.repository.OperationRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private OperationRepository operationRepository;

    @GetMapping
    public List<PartnerDTO> getPartners(@RequestParam(required = false) Boolean archived) {
        List<Partner> partners;
        if (archived == null) {
            partners = partnerRepository.findAll();
        } else {
            partners = partnerRepository.findAllByArchived(archived);
        }
        return partners.stream().map(Partner::getDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerDTO> getPartner(@PathVariable(name = "id") BigInteger id,
                                                      @RequestParam(required = false) Boolean archived) {
        Optional<Partner> result;
        if (archived == null) {
            result = partnerRepository.findById(id);
        } else {
            result = partnerRepository.findByIdAndArchived(id, archived);
        }
        if (result.isPresent()) {
            return new ResponseEntity(result.get().getDTO(), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<PartnerDTO> createPartner(
            @Validated(PartnerDTO.New.class) @RequestBody PartnerDTO partnerDTO) {
        Partner savedPartner = partnerRepository.save(new Partner(partnerDTO));
        return new ResponseEntity(savedPartner.getDTO(), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<PartnerDTO> updatePartner(
            @Validated(PartnerDTO.Existing.class) @RequestBody PartnerDTO partnerDTO) {
        Optional<Partner> result = partnerRepository.findById(partnerDTO.getId());
        if (!result.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Partner partner = result.get();
        partner.update(partnerDTO, hasOperations(partner));
        Partner savedPartner = partnerRepository.save(partner);
        return new ResponseEntity(savedPartner.getDTO(), HttpStatus.OK);
    }

    /**
     * Удалить контрагента, если с ним нет связанных операций, иначе переместить в архив.
     * @param id - идентификатор контрагента
     * @return статус удаления
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deletePartner(@PathVariable(name = "id") BigInteger id) {
        Optional<Partner> partnerResult = partnerRepository.findById(id);
        if (partnerResult.isPresent()) {
            Partner partner = partnerResult.get();
            if (hasOperations(partner)) {
                partner.archive();
                partnerRepository.save(partner);
            } else {
                partnerRepository.delete(partner);
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean hasOperations(Partner partner) {
        Operation operation = operationRepository
                .findFirstBySourceMoneyAccountOrDestinationMoneyAccount(partner, partner);
        return operation != null;
    }



}