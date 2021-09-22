package beans;

import data.Person;
import data.PersonRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TreeMap;

@Configuration
public class DataConfiguration {
    @Bean
    public PersonRepository dataPerson(){
        PersonRepository personRepository= new PersonRepository(100000);
        personRepository.add(1,new Person("Ксан ЖОПА", 10000));
        personRepository.add(2,new Person("Кейт ЖОПА", 2000000));
        return personRepository;
    }
}
