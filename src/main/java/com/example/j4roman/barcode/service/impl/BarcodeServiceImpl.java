package com.example.j4roman.barcode.service.impl;

import com.example.j4roman.barcode.persistance.dao.BCAlgorithmDAO;
import com.example.j4roman.barcode.persistance.dao.ClientDAO;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.persistance.entities.Client;
import com.example.j4roman.barcode.service.BarcodeService;
import com.example.j4roman.barcode.service.dto.ToBarcodeRequestDTO;
import com.example.j4roman.barcode.service.dto.ToBarcodeResponseDTO;
import com.example.j4roman.barcode.service.dto.ToBarcodeResponseItemDTO;
import com.example.j4roman.barcode.service.utils.ToBarcode;
import com.example.j4roman.barcode.service.utils.ToBarcodeFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BarcodeServiceImpl implements BarcodeService {

    private static final Logger logger = LogManager.getLogger(BarcodeServiceImpl.class);

    @Autowired
    BCAlgorithmDAO bcAlgorithmDAO;

    @Autowired
    ClientDAO clientDAO;

    @Override
    @Transactional
    public ToBarcodeResponseDTO toBarcode(ToBarcodeRequestDTO requestBody) {
        // Check client
        String cltUpperCode = requestBody.getClientCode().toUpperCase();
        Client client = clientDAO.getByCode(cltUpperCode);
        if (client == null) {
            return ToBarcodeResponseDTO.clientNotFound(cltUpperCode);
        }
        // Check algorithm
        String algUpperName = requestBody.getAlgorithm().toUpperCase();
        BCAlgorithm algorithm = bcAlgorithmDAO.getByName(algUpperName);
        if (algorithm == null) {
            return ToBarcodeResponseDTO.algorithmNotFound(algUpperName);
        }
        // Check client-algorithm access
        String specValue = clientDAO.getValueByClientAlgorithm(client, algorithm);
        if (specValue == null) {
            return ToBarcodeResponseDTO.clientAlgorithmNotBind(client.getCode(), algorithm.getName());
        }
        List<ToBarcodeResponseItemDTO> results = new ArrayList<>();
        ToBarcode toBarcode = ToBarcodeFactory.create(algorithm, specValue);
        for (String value : requestBody.getValues()) {
            if (!value.matches(algorithm.getPattern())) {
                logger.info("Algorithm {} doesn\'t match pattern {}", algUpperName, algorithm.getPattern());
                results.add(ToBarcodeResponseItemDTO.errorDoesntMatch(algUpperName, algorithm.getPattern()));
                continue;
            }
            try {
                results.add(ToBarcodeResponseItemDTO.okResult(toBarcode.exec(value)));
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                logger.error(sw.toString());
                results.add(ToBarcodeResponseItemDTO.errorUnexpected());
            }
        }
        return new ToBarcodeResponseDTO(results);
    }
}
