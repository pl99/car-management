package ru.advantum.car.management.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import ru.advantum.commons.test.mvc.BaseControllerTest;

/**
 * Test class for the {@link OwnershipController}
 */
public class OwnershipControllerTest extends BaseControllerTest {

        private final String purchase = "/rest/ownerships/purchase";
        private final String sell = "/rest/ownerships/sell";

    @Test
    @Transactional
    public void purchase() {
        String dto = """
                {
                  "carId": 3,
                  "ownerId": 4,
                  "purchaseDate": "2024-11-04"
                }""";
        mockPostRequest(purchase, dto);
    }

    @Test
    @Transactional
    public void sell() {
        String dto = """
                {
                  "carId": 5,
                  "ownerId": 1,
                  "saleDate": "2024-11-04"
                }""";

        mockPostRequest(sell, dto);
    }

    @Override
    protected void assertionForPost(MvcResult result, String url, String content) {
        switch (url){
            case purchase: assertPurchase(result);break;
            case sell:assertSale(result);break;
        }
    }

    @SneakyThrows
    private void assertSale(MvcResult result) {
        boolean contains = result.getResponse().getContentAsString().contains("\"saleDate\":\"2024-11-04\"");
        Assertions.assertTrue(contains);

    }

    @SneakyThrows
    private void assertPurchase(MvcResult result) {
        boolean contains = result.getResponse().getContentAsString().contains("\"purchaseDate\":\"2024-11-04\"");
        Assertions.assertTrue(contains);
    }

}
