package com.example.j4roman.barcode.service.impl;

import com.example.j4roman.barcode.persistance.dao.BCAlgorithmDAO;
import com.example.j4roman.barcode.persistance.dao.ClientDAO;
import com.example.j4roman.barcode.persistance.entities.BCAlgorithm;
import com.example.j4roman.barcode.persistance.entities.Client;
import com.example.j4roman.barcode.service.BarcodeService;
import com.example.j4roman.barcode.service.dto.ToBarcodeRequestDTO;
import com.example.j4roman.barcode.service.dto.ToBarcodeResponseDTO;
import com.example.j4roman.barcode.service.dto.ToBarcodeResponseItemDTO;
import com.example.j4roman.barcode.service.exceptions.DAOException;
import com.example.j4roman.barcode.service.exceptions.UnexpectedDAOException;
import com.example.j4roman.barcode.service.utils.BarcodeCheckFailedException;
import com.example.j4roman.barcode.service.utils.BarcodeFuncFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class BarcodeServiceImpl implements BarcodeService {

    private static final Logger logger = LogManager.getLogger(BarcodeServiceImpl.class);

    @Autowired
    BCAlgorithmDAO bcAlgorithmDAO;

    @Autowired
    ClientDAO clientDAO;

    /**
     * The method is used to generate barcode from values list
     * and to parse it back to values depends on <code>isParse</code>
     * @param requestBody body of income request
     * @param isParse if <code>true</code> then perform parse barcode to value
     *                else - generate barcode from value
     * @return response body with transformed values
     */
    @Override
    @Transactional
    public ToBarcodeResponseDTO generate(ToBarcodeRequestDTO requestBody, boolean isParse) {
        try {
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
            // List to hold results
            List<ToBarcodeResponseItemDTO> results = new ArrayList<>();

            // Function to transform every given value to barcode
            // or barcode to value
            Function<String, String> generateValueFunc = null;
            if (!isParse) {
                generateValueFunc = BarcodeFuncFactory.toBarcode(algorithm, specValue);
            } else {
                generateValueFunc = BarcodeFuncFactory.fromBarcode(algorithm, specValue);
            }
            for (String value : requestBody.getValues()) {
                String pattern = !isParse ? algorithm.getInPattern() : algorithm.getOutPattern();
                // Test value for correct format
                if (!value.matches(pattern)) {
                    logger.info("Value {} doesn\'t match pattern {}", value, pattern);
                    results.add(ToBarcodeResponseItemDTO.errorDoesntMatch(value, pattern));
                    continue;
                }
                try {
                    // Apply function
                    String generatedValue = generateValueFunc.apply(value);
                    results.add(ToBarcodeResponseItemDTO.okResult(generatedValue));
                } catch (BarcodeCheckFailedException e) {
                    results.add(ToBarcodeResponseItemDTO.errorDescr(e.getMessage()));
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    logger.error(sw.toString());
                    results.add(ToBarcodeResponseItemDTO.errorUnexpected());
                }
            }
            return new ToBarcodeResponseDTO(results);
        } catch (HibernateException e) {
            throw new UnexpectedDAOException(e);
        }
    }
}