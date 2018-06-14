package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.College;
import com.mycompany.myapp.repository.CollegeRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing College.
 */
@RestController
@RequestMapping("/app")
public class CollegeResource {

    private final Logger log = LoggerFactory.getLogger(CollegeResource.class);

    @Inject
    private CollegeRepository collegeRepository;

    /**
     * POST  /rest/colleges -> Create a new college.
     */
    @RequestMapping(value = "/rest/colleges",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public void create(@RequestBody College college) {
        log.debug("REST request to save College : {}", college);
        collegeRepository.save(college);
    }

    /**
     * GET  /rest/colleges -> get all the colleges.
     */
    @RequestMapping(value = "/rest/colleges",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
  
    public List<College> getAll() {
        log.debug("REST request to get all Colleges");
        return collegeRepository.findAll();
    }

    /**
     * GET  /rest/colleges/:id -> get the "id" college.
     */
    @RequestMapping(value = "/rest/colleges/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<College> get(@PathVariable Long id) {
        log.debug("REST request to get College : {}", id);
        return Optional.ofNullable(collegeRepository.findOne(id))
            .map(college -> new ResponseEntity<>(
                college,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/colleges/:id -> delete the "id" college.
     */
    @RequestMapping(value = "/rest/colleges/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete College : {}", id);
        collegeRepository.delete(id);
    }
}
