package com.example.demo.controller;

import com.example.demo.POJO.*;

import com.example.demo.configure.JwtUser;
import com.example.demo.repository.UserFileRepository;
import com.example.demo.service.GraphService;
import com.example.demo.util.JwtTokenUtil;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/csv")
public class GraphCSVController {

    @Autowired
    private  HttpServletResponse response;

    @Autowired
    private GraphService graphService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserFileRepository userFileRepository;

    @GetMapping("/{fileId}")
    public void exportFileCsv( @PathVariable String fileId ) throws java.io.IOException {
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!userFileRepository.existsByIdAndUserIdAndDelete(fileId , user.getId() , false))return ;

        String filename = fileId+".csv";

        List<Node> nodes = graphService.getNodesByFile("U"+user.getId(), "F"+fileId);
//        List<RelationWarp> relawarps = graphService.getRelationsByFile("U"+user.getId(), "F"+fileId);
        List<RelaCSVWarp> relaCSVWarps = graphService.getRelaCSV("U"+user.getId(), "F"+fileId);
        CSVPrinter csvPrinter=null;
        try {
            response.setContentType("text/csv;charset=utf8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"");
//            csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT.withHeader("field1", "field2", "field3"));
            PrintWriter writer = response.getWriter();
//            writer.write('\ufeff');
//            writer.write(new char[]{(char)0xef,(char)0xbb,(char)0xbf});
//            writer.write("fuck");
            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withQuote(null));
            for (RelaCSVWarp rela : relaCSVWarps) {
                csvPrinter.printRecord(Arrays.asList(rela.getStartStr() ,rela.getEndStr(), rela.getRelaUnit().getRelationName()));
//                System.out.println(rela.getStartStr() );
//                System.out.println(rela.getEndStr());
//                System.out.println(rela.getRelaUnit().getRelationName());
//                System.out.println("-------------------");
            }
//            for (Node node: nodes) {
//                csvPrinter.printRecord(Arrays.asList());
//            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(csvPrinter != null)
                csvPrinter. close();
        }
    }

}
