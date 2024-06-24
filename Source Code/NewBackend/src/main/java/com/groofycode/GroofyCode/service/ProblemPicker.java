package com.groofycode.GroofyCode.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groofycode.GroofyCode.dto.PlayerDTO;
import com.groofycode.GroofyCode.dto.ProblemDTO;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Service
public class ProblemPicker {
    private Map<Integer, List<ProblemDTO>> problems;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public ProblemPicker() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        try {
            problems = processProblems("problems.csv");
        } catch (Exception e) {
            e.printStackTrace();
            problems = new HashMap<>(); // Initialize to an empty map in case of an error
        }
    }

    private Map<Integer, List<ProblemDTO>> processProblems(String filePath) throws Exception {
        List<ProblemDTO> problems = readCsv(filePath);

        // Group by rating and sort by solved count in increasing order
        return problems.stream()
                .collect(Collectors.groupingBy(
                        ProblemDTO::getRating,
                        TreeMap::new,  // Use TreeMap to maintain the natural order of keys
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    list.sort(Comparator.comparingInt(ProblemDTO::getSolvedCount));
                                    return list;
                                }
                        )
                ));
    }

    private List<ProblemDTO> readCsv(String filePath) throws Exception {
        List<ProblemDTO> problems = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            reader.readNext(); // skip header
            while ((nextLine = reader.readNext()) != null) {
                String name = nextLine[0];
                int solvedCount = Integer.parseInt(nextLine[1]);
                String contestId = nextLine[2];
                String index = nextLine[3];
                int rating = Integer.parseInt(nextLine[4]);
                problems.add(new ProblemDTO(name, solvedCount, contestId, index, rating));
            }
        }
        return problems;
    }


    public ResponseEntity<Object> pickProblem(PlayerDTO playerDTO, List<ProblemDTO> solvedProblems) throws Exception {
        String apiUrl = "http://localhost:5000/predict";
        String response = restTemplate.postForObject(apiUrl, playerDTO, String.class);
        JsonNode jsonResponse = objectMapper.readTree(response);
        double expectedRating = jsonResponse.get("expected_rating").asDouble();
        int count = (int)expectedRating / 100;
        if((int)expectedRating % 100 != 0)count++;
        int rating = 100 * count;
        while (solvedProblems.size() == problems.get(rating).size())rating += 100;

        List<ProblemDTO> problemsByRating = problems.get(rating);
        List<ProblemDTO> unSolvedProblems = new ArrayList<>();
        for(ProblemDTO problemDTO : problemsByRating) {
            if(solvedProblems.contains(problemDTO)) continue;
            unSolvedProblems.add(problemDTO);
        }
        double percentile = (double) solvedProblems.size() / problemsByRating.size();
        int index = (int) Math.round(percentile * unSolvedProblems.size());
        index = Math.min(Math.max(index, 0), unSolvedProblems.size() - 1);

        return ResponseEntity.ok("https://codeforces.com/contest/" + unSolvedProblems.get(index).getContestId() + "/problem/" + unSolvedProblems.get(index).getIndex());
    }

}
