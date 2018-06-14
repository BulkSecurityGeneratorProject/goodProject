package com.mycompany.myapp.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.College;
import com.mycompany.myapp.repository.CollegeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CollegeResource REST controller.
 *
 * @see CollegeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CollegeResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_FEES = 0;
    private static final Integer UPDATED_FEES = 1;
    

    @Inject
    private CollegeRepository collegeRepository;

    private MockMvc restCollegeMockMvc;

    private College college;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CollegeResource collegeResource = new CollegeResource();
        ReflectionTestUtils.setField(collegeResource, "collegeRepository", collegeRepository);
        this.restCollegeMockMvc = MockMvcBuilders.standaloneSetup(collegeResource).build();
    }

    @Before
    public void initTest() {
        college = new College();
        college.setName(DEFAULT_NAME);
        college.setFees(DEFAULT_FEES);
    }

    @Test
    @Transactional
    public void createCollege() throws Exception {
        // Validate the database is empty
        assertThat(collegeRepository.findAll()).hasSize(0);

        // Create the College
        restCollegeMockMvc.perform(post("/app/rest/colleges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(college)))
                .andExpect(status().isOk());

        // Validate the College in the database
        List<College> colleges = collegeRepository.findAll();
        assertThat(colleges).hasSize(1);
        College testCollege = colleges.iterator().next();
        assertThat(testCollege.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollege.getFees()).isEqualTo(DEFAULT_FEES);
    }

    @Test
    @Transactional
    public void getAllColleges() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        // Get all the colleges
        restCollegeMockMvc.perform(get("/app/rest/colleges"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(college.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].fees").value(DEFAULT_FEES));
    }

    @Test
    @Transactional
    public void getCollege() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        // Get the college
        restCollegeMockMvc.perform(get("/app/rest/colleges/{id}", college.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(college.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fees").value(DEFAULT_FEES));
    }

    @Test
    @Transactional
    public void getNonExistingCollege() throws Exception {
        // Get the college
        restCollegeMockMvc.perform(get("/app/rest/colleges/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollege() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        // Update the college
        college.setName(UPDATED_NAME);
        college.setFees(UPDATED_FEES);
        restCollegeMockMvc.perform(post("/app/rest/colleges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(college)))
                .andExpect(status().isOk());

        // Validate the College in the database
        List<College> colleges = collegeRepository.findAll();
        assertThat(colleges).hasSize(1);
        College testCollege = colleges.iterator().next();
        assertThat(testCollege.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollege.getFees()).isEqualTo(UPDATED_FEES);;
    }

    @Test
    @Transactional
    public void deleteCollege() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        // Get the college
        restCollegeMockMvc.perform(delete("/app/rest/colleges/{id}", college.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<College> colleges = collegeRepository.findAll();
        assertThat(colleges).hasSize(0);
    }
}
