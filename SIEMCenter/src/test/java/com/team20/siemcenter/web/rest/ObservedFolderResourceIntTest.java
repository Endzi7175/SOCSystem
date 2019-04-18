package com.team20.siemcenter.web.rest;

import com.team20.siemcenter.SiemcenterApp;

import com.team20.siemcenter.domain.ObservedFolder;
import com.team20.siemcenter.repository.ObservedFolderRepository;
import com.team20.siemcenter.repository.search.ObservedFolderSearchRepository;
import com.team20.siemcenter.service.ObservedFolderService;
import com.team20.siemcenter.service.dto.ObservedFolderDTO;
import com.team20.siemcenter.service.mapper.ObservedFolderMapper;
import com.team20.siemcenter.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.team20.siemcenter.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ObservedFolderResource REST controller.
 *
 * @see ObservedFolderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiemcenterApp.class)
public class ObservedFolderResourceIntTest {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FILTER_REGEX = "AAAAAAAAAA";
    private static final String UPDATED_FILTER_REGEX = "BBBBBBBBBB";

    @Autowired
    private ObservedFolderRepository observedFolderRepository;

    @Mock
    private ObservedFolderRepository observedFolderRepositoryMock;

    @Autowired
    private ObservedFolderMapper observedFolderMapper;

    @Mock
    private ObservedFolderService observedFolderServiceMock;

    @Autowired
    private ObservedFolderService observedFolderService;

    /**
     * This repository is mocked in the com.team20.siemcenter.repository.search test package.
     *
     * @see com.team20.siemcenter.repository.search.ObservedFolderSearchRepositoryMockConfiguration
     */
    @Autowired
    private ObservedFolderSearchRepository mockObservedFolderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restObservedFolderMockMvc;

    private ObservedFolder observedFolder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObservedFolderResource observedFolderResource = new ObservedFolderResource(observedFolderService);
        this.restObservedFolderMockMvc = MockMvcBuilders.standaloneSetup(observedFolderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObservedFolder createEntity(EntityManager em) {
        ObservedFolder observedFolder = new ObservedFolder()
            .path(DEFAULT_PATH)
            .description(DEFAULT_DESCRIPTION)
            .filterRegex(DEFAULT_FILTER_REGEX);
        return observedFolder;
    }

    @Before
    public void initTest() {
        observedFolder = createEntity(em);
    }

    @Test
    @Transactional
    public void createObservedFolder() throws Exception {
        int databaseSizeBeforeCreate = observedFolderRepository.findAll().size();

        // Create the ObservedFolder
        ObservedFolderDTO observedFolderDTO = observedFolderMapper.toDto(observedFolder);
        restObservedFolderMockMvc.perform(post("/api/observed-folders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observedFolderDTO)))
            .andExpect(status().isCreated());

        // Validate the ObservedFolder in the database
        List<ObservedFolder> observedFolderList = observedFolderRepository.findAll();
        assertThat(observedFolderList).hasSize(databaseSizeBeforeCreate + 1);
        ObservedFolder testObservedFolder = observedFolderList.get(observedFolderList.size() - 1);
        assertThat(testObservedFolder.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testObservedFolder.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testObservedFolder.getFilterRegex()).isEqualTo(DEFAULT_FILTER_REGEX);

        // Validate the ObservedFolder in Elasticsearch
        verify(mockObservedFolderSearchRepository, times(1)).save(testObservedFolder);
    }

    @Test
    @Transactional
    public void createObservedFolderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = observedFolderRepository.findAll().size();

        // Create the ObservedFolder with an existing ID
        observedFolder.setId(1L);
        ObservedFolderDTO observedFolderDTO = observedFolderMapper.toDto(observedFolder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservedFolderMockMvc.perform(post("/api/observed-folders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observedFolderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObservedFolder in the database
        List<ObservedFolder> observedFolderList = observedFolderRepository.findAll();
        assertThat(observedFolderList).hasSize(databaseSizeBeforeCreate);

        // Validate the ObservedFolder in Elasticsearch
        verify(mockObservedFolderSearchRepository, times(0)).save(observedFolder);
    }

    @Test
    @Transactional
    public void getAllObservedFolders() throws Exception {
        // Initialize the database
        observedFolderRepository.saveAndFlush(observedFolder);

        // Get all the observedFolderList
        restObservedFolderMockMvc.perform(get("/api/observed-folders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observedFolder.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].filterRegex").value(hasItem(DEFAULT_FILTER_REGEX.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllObservedFoldersWithEagerRelationshipsIsEnabled() throws Exception {
        ObservedFolderResource observedFolderResource = new ObservedFolderResource(observedFolderServiceMock);
        when(observedFolderServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restObservedFolderMockMvc = MockMvcBuilders.standaloneSetup(observedFolderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restObservedFolderMockMvc.perform(get("/api/observed-folders?eagerload=true"))
        .andExpect(status().isOk());

        verify(observedFolderServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllObservedFoldersWithEagerRelationshipsIsNotEnabled() throws Exception {
        ObservedFolderResource observedFolderResource = new ObservedFolderResource(observedFolderServiceMock);
            when(observedFolderServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restObservedFolderMockMvc = MockMvcBuilders.standaloneSetup(observedFolderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restObservedFolderMockMvc.perform(get("/api/observed-folders?eagerload=true"))
        .andExpect(status().isOk());

            verify(observedFolderServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getObservedFolder() throws Exception {
        // Initialize the database
        observedFolderRepository.saveAndFlush(observedFolder);

        // Get the observedFolder
        restObservedFolderMockMvc.perform(get("/api/observed-folders/{id}", observedFolder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(observedFolder.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.filterRegex").value(DEFAULT_FILTER_REGEX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingObservedFolder() throws Exception {
        // Get the observedFolder
        restObservedFolderMockMvc.perform(get("/api/observed-folders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObservedFolder() throws Exception {
        // Initialize the database
        observedFolderRepository.saveAndFlush(observedFolder);

        int databaseSizeBeforeUpdate = observedFolderRepository.findAll().size();

        // Update the observedFolder
        ObservedFolder updatedObservedFolder = observedFolderRepository.findById(observedFolder.getId()).get();
        // Disconnect from session so that the updates on updatedObservedFolder are not directly saved in db
        em.detach(updatedObservedFolder);
        updatedObservedFolder
            .path(UPDATED_PATH)
            .description(UPDATED_DESCRIPTION)
            .filterRegex(UPDATED_FILTER_REGEX);
        ObservedFolderDTO observedFolderDTO = observedFolderMapper.toDto(updatedObservedFolder);

        restObservedFolderMockMvc.perform(put("/api/observed-folders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observedFolderDTO)))
            .andExpect(status().isOk());

        // Validate the ObservedFolder in the database
        List<ObservedFolder> observedFolderList = observedFolderRepository.findAll();
        assertThat(observedFolderList).hasSize(databaseSizeBeforeUpdate);
        ObservedFolder testObservedFolder = observedFolderList.get(observedFolderList.size() - 1);
        assertThat(testObservedFolder.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testObservedFolder.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testObservedFolder.getFilterRegex()).isEqualTo(UPDATED_FILTER_REGEX);

        // Validate the ObservedFolder in Elasticsearch
        verify(mockObservedFolderSearchRepository, times(1)).save(testObservedFolder);
    }

    @Test
    @Transactional
    public void updateNonExistingObservedFolder() throws Exception {
        int databaseSizeBeforeUpdate = observedFolderRepository.findAll().size();

        // Create the ObservedFolder
        ObservedFolderDTO observedFolderDTO = observedFolderMapper.toDto(observedFolder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservedFolderMockMvc.perform(put("/api/observed-folders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observedFolderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObservedFolder in the database
        List<ObservedFolder> observedFolderList = observedFolderRepository.findAll();
        assertThat(observedFolderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ObservedFolder in Elasticsearch
        verify(mockObservedFolderSearchRepository, times(0)).save(observedFolder);
    }

    @Test
    @Transactional
    public void deleteObservedFolder() throws Exception {
        // Initialize the database
        observedFolderRepository.saveAndFlush(observedFolder);

        int databaseSizeBeforeDelete = observedFolderRepository.findAll().size();

        // Delete the observedFolder
        restObservedFolderMockMvc.perform(delete("/api/observed-folders/{id}", observedFolder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ObservedFolder> observedFolderList = observedFolderRepository.findAll();
        assertThat(observedFolderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ObservedFolder in Elasticsearch
        verify(mockObservedFolderSearchRepository, times(1)).deleteById(observedFolder.getId());
    }

    @Test
    @Transactional
    public void searchObservedFolder() throws Exception {
        // Initialize the database
        observedFolderRepository.saveAndFlush(observedFolder);
        when(mockObservedFolderSearchRepository.search(queryStringQuery("id:" + observedFolder.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(observedFolder), PageRequest.of(0, 1), 1));
        // Search the observedFolder
        restObservedFolderMockMvc.perform(get("/api/_search/observed-folders?query=id:" + observedFolder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observedFolder.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].filterRegex").value(hasItem(DEFAULT_FILTER_REGEX)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservedFolder.class);
        ObservedFolder observedFolder1 = new ObservedFolder();
        observedFolder1.setId(1L);
        ObservedFolder observedFolder2 = new ObservedFolder();
        observedFolder2.setId(observedFolder1.getId());
        assertThat(observedFolder1).isEqualTo(observedFolder2);
        observedFolder2.setId(2L);
        assertThat(observedFolder1).isNotEqualTo(observedFolder2);
        observedFolder1.setId(null);
        assertThat(observedFolder1).isNotEqualTo(observedFolder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservedFolderDTO.class);
        ObservedFolderDTO observedFolderDTO1 = new ObservedFolderDTO();
        observedFolderDTO1.setId(1L);
        ObservedFolderDTO observedFolderDTO2 = new ObservedFolderDTO();
        assertThat(observedFolderDTO1).isNotEqualTo(observedFolderDTO2);
        observedFolderDTO2.setId(observedFolderDTO1.getId());
        assertThat(observedFolderDTO1).isEqualTo(observedFolderDTO2);
        observedFolderDTO2.setId(2L);
        assertThat(observedFolderDTO1).isNotEqualTo(observedFolderDTO2);
        observedFolderDTO1.setId(null);
        assertThat(observedFolderDTO1).isNotEqualTo(observedFolderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(observedFolderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(observedFolderMapper.fromId(null)).isNull();
    }
}
