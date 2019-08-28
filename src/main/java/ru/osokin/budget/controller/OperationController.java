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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.osokin.budget.dto.OperationDTO;
import ru.osokin.budget.service.OperationService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @GetMapping
    public List<OperationDTO> getOperations() {
        return operationService.getOperations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDTO> getOperation(@PathVariable(name = "id") BigInteger id) {
        Optional<OperationDTO> result = operationService.getOperation(id);
        if (result.isPresent()) {
            return new ResponseEntity(result, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<OperationDTO> createOperation(
            @Validated(OperationDTO.New.class) @RequestBody OperationDTO operationDTO) {
        return new ResponseEntity(operationService.createOperation(operationDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<OperationDTO> updateOperation(
            @Validated(OperationDTO.Existing.class) @RequestBody OperationDTO operationDTO) {
        Optional<OperationDTO> result = operationService.updateOperation(operationDTO);
        if (result.isPresent()) {
            return new ResponseEntity(result, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOperation(@PathVariable(name = "id") BigInteger id) {
        if (operationService.deleteOperation(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
