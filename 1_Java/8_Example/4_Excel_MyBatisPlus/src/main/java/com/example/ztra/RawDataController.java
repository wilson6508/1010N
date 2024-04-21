package com.example.ztra;

import com.example.ztra.dto.request.RawDataRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

@RestController
@RequestMapping("/rawData")
public class RawDataController {

    @Resource
    private RawDataService rawDataService;

    @PostMapping("/downloadExcel")
    public void downloadExcel(@RequestBody RawDataRequest rawDataRequest, HttpServletResponse response) {
        rawDataService.downloadExcel(rawDataRequest, response);
    }

    @PostMapping("/createExcel")
    public String createExcel(@RequestBody RawDataRequest rawDataRequest) {
        rawDataService.createExcel(rawDataRequest);
        return "done";
    }

    @GetMapping("/readJsonExcel")
    public LinkedList<HashMap<String, Object>> readJsonExcel(@RequestParam("fileName") String fileName) {
        return rawDataService.readJsonExcel(fileName);
    }

    @GetMapping("/insertToDb")
    public String insertToDb() throws IOException {
        rawDataService.insertToDb();
        return "insertToDb";
    }

}
