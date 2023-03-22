package com.yh.supermarket.pricer.kata;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBeanBuilder;
import com.yh.supermarket.pricer.kata.controller.MainController;
import com.yh.supermarket.pricer.kata.factory.PromotionStrategyFactory;
import com.yh.supermarket.pricer.kata.model.Item;
import com.yh.supermarket.pricer.kata.model.Promotion;
import com.yh.supermarket.pricer.kata.service.BasketService;
import com.yh.supermarket.pricer.kata.service.ItemService;
import com.yh.supermarket.pricer.kata.service.PromotionEngineService;
import com.yh.supermarket.pricer.kata.service.PromotionService;
import com.yh.supermarket.pricer.kata.service.impl.BasketServiceImpl;
import com.yh.supermarket.pricer.kata.service.impl.PromotionEngineServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = MainController.class)
public class MainControllerTest {
    @Autowired
    private MainController mainController;
    @MockBean
    private PromotionService promotionService;
    @MockBean
    private ItemService itemService;
    @MockBean
    private BasketService basketService;


    private List<Promotion> promotions = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    private String allItemResponse;
    private String allPromotionsResponse;

    private String pastaItemAsString;
    private String priceWeightDiscountPromotionAsString;


    @BeforeEach
    public void initTests() throws IOException {
        if (items.isEmpty()) {
            this.items = this.readDataFromCsvFile("items.csv", Item.class);
        }
        if (StringUtils.isEmpty(allItemResponse)) {
            this.allItemResponse = readResponseAsString("allItemResponse.json");
        }

        if (StringUtils.isEmpty(allPromotionsResponse)) {
            this.allPromotionsResponse = readResponseAsString("allPromotionsResponse.json");
        }

        if (StringUtils.isEmpty(pastaItemAsString)) {
            pastaItemAsString = this.readResponseAsString("item.json");
        }

        if (StringUtils.isEmpty(priceWeightDiscountPromotionAsString)) {
            priceWeightDiscountPromotionAsString = this.readResponseAsString("promotion.json");
        }

        Gson gson = new Gson();
        Item pastaItem = gson.fromJson(pastaItemAsString, Item.class);
        Mockito.when(itemService.findAllItems()).thenReturn(this.items);
        Mockito.when(itemService.addItem(any(Item.class))).thenReturn(pastaItem);

        Mockito.when(itemService.getItemById(any(String.class))).thenReturn(pastaItem);
        if (promotions.isEmpty()) {
            this.promotions = this.readDataFromCsvFile("promotions.csv", Promotion.class);
            this.promotions.forEach(promotion -> {
                Item item = this.findItemById(promotion.getItemId());
                promotion.setItem(item);
            });
        }
        Promotion priceWeightDiscountPromotion = gson.fromJson(this.priceWeightDiscountPromotionAsString, Promotion.class);
        Mockito.when(promotionService.findAllPromotions()).thenReturn(this.promotions);
        Mockito.when(promotionService.addPromotion(any(Promotion.class))).thenReturn(priceWeightDiscountPromotion);
        if (!promotions.isEmpty()) {
            Map<Item, Set<Promotion>> promotionItemMap = new HashMap<>();
            for (Promotion promotion : promotions) {
                Set<Promotion> promotionSet = null;
                if (promotionItemMap.containsKey(promotion.getItem())) {
                    promotionSet = promotionItemMap.get(promotion.getItem());
                } else {
                    promotionSet = new HashSet<>();
                }
                promotionSet.add(promotion);
                promotionItemMap.put(promotion.getItem(), promotionSet);
            }
            Mockito.when(promotionService.getPromotionMapByItem()).thenReturn(promotionItemMap);
        }
        PromotionStrategyFactory promotionStrategyFactory = new PromotionStrategyFactory();
        PromotionEngineService promotionEngineService = new PromotionEngineServiceImpl(promotionStrategyFactory);
        this.basketService = new BasketServiceImpl(this.itemService, this.promotionService, promotionEngineService);

    }

    @Test
    public void test_find_all_items() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/item/all")).andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals(allItemResponse, result.getResponse().getContentAsString().replaceAll("\\s+", ""));
    }

    @Test
    public void test_create_new_item() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/item/add").content(pastaItemAsString).contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        Assertions.assertEquals(pastaItemAsString, result.getResponse().getContentAsString());
    }

    @Test
    public void test_find_all_promotions() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/promotion/all")).andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals(allPromotionsResponse, result.getResponse().getContentAsString().replaceAll("\\s+", ""));
    }

    @Test
    public void test_create_new_promotion() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/promotion/add").content(priceWeightDiscountPromotionAsString).contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        Assertions.assertEquals(priceWeightDiscountPromotionAsString, result.getResponse().getContentAsString());
    }

    @Test
    public void test_calculate_total_without_promotion() throws Exception {
        this.mainController = new MainController(promotionService, itemService, this.basketService);
        String baskItemsRequestAsString = readResponseAsString("basketItemsRequest.json");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/basket/calculate-total-without-promotion").content(baskItemsRequestAsString).contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals("19.80", result.getResponse().getContentAsString());
    }

    @Test
    public void test_calculate_total_with_promotion() throws Exception {
        this.mainController = new MainController(promotionService, itemService, this.basketService);
        String baskItemsRequestAsString = readResponseAsString("basketItemsRequest.json");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/basket/calculate-total-with-promotion").content(baskItemsRequestAsString).contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertNotEquals("19.80", result.getResponse().getContentAsString());

        Assertions.assertEquals("17.10", result.getResponse().getContentAsString());
    }


    private Item findItemById(String id) {
        return this.items.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }

    private <Any> List<Any> readDataFromCsvFile(String fileName, Class className) throws FileNotFoundException {
        Path resourceDirectory = Paths.get("src", "test", "resources", "data");
        String filePath = String.format("%s/%s", resourceDirectory.toString(), fileName);
        return new CsvToBeanBuilder(new FileReader(filePath)).withType(className).build().parse();
    }

    private String readResponseAsString(String fileName) throws IOException {
        Path filePath = Paths.get("src", "test", "resources", "data", fileName);
        String response = new String(Files.readAllBytes(filePath));
        return response.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\\s+", "");
    }
}

