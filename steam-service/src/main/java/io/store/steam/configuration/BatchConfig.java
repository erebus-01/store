package io.store.steam.configuration;

import io.store.steam.model.Game;
import io.store.steam.repository.FeatureRepository;
import io.store.steam.repository.GameRepository;
import io.store.steam.repository.GenreRepository;
import io.store.steam.repository.SlideImageRepository;
import io.store.steam.utils.DatePropertyEditor;
import io.store.steam.utils.GameFieldSetMapper;
import io.store.steam.utils.GameProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final GameRepository gameRepository;
    private final JobRepository jobRepository;
    private final FeatureRepository featureRepository;
    private final GenreRepository genreRepository;
    private final SlideImageRepository slideImageRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public FlatFileItemReader<Game> itemReader() {
        FlatFileItemReader<Game> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/game-info.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    @Bean
    public GameProcessor gameProcessor() {
        return new GameProcessor();
    }

    @Bean
    public RepositoryItemWriter<Game> repositoryItemWriter() {
        RepositoryItemWriter<Game> writer = new RepositoryItemWriter<>();
        writer.setRepository(gameRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step importStep() {
        return new StepBuilder("csvBuilder", jobRepository)
                .<Game, Game>chunk(10, platformTransactionManager)
                .reader(itemReader())
                .processor(gameProcessor())
                .writer(repositoryItemWriter())
                .build();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder("importGames", jobRepository)
                .start(importStep())
                .build();
    }

    private LineMapper<Game> lineMapper() {
        DefaultLineMapper<Game> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("title", "description", "imageUrl", "price", "releaseDate", "developer", "publisher", "platform", "country", "status", "stock", "discount", "ageRating");
//        lineTokenizer.setNames("title", "description", "imageUrl", "price", "releaseDate", "developer", "publisher", "platform", "country", "status", "stock", "discount", "ageRating", "features", "genres", "slideImages");


        BeanWrapperFieldSetMapper<Game> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

        fieldSetMapper.setTargetType(Game.class);
        fieldSetMapper.setCustomEditors(Collections.singletonMap(Date.class, new DatePropertyEditor()));

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
//        lineMapper.setFieldSetMapper(new GameFieldSetMapper(featureRepository, genreRepository, slideImageRepository));

        return lineMapper;
    }

}
