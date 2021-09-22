package controllers;

import beans.DataConfiguration;
import data.DataJsonForSetPerson;
import data.Person;
import data.PersonRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RestController
@Import(DataConfiguration.class)
public class Demo {
    @Autowired
    private PersonRepository personRepository;
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String sendData(){
        return personRepository.getAll();
    }
    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String sendDataById(@PathVariable("id") int id){
        return personRepository.getById(id);
    }
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addPerson(@RequestBody DataJsonForSetPerson dataJsonForSetPerson){
        return personRepository.add(dataJsonForSetPerson.getId(), new Person(dataJsonForSetPerson.getName(), dataJsonForSetPerson.getTimelife()));
    }
    @DeleteMapping(value = "/data/{id}")
    @ResponseBody
    public String deleteDataById(@PathVariable("id") int id){
        System.out.println(id);
        return personRepository.removeById(id);
    }
    @GetMapping(value = "/dump")
    @ResponseBody
    public void saveDataInFileAndSendToClient(HttpServletResponse response){
        try{
            response.getOutputStream();
            IOUtils.copy(new FileInputStream(personRepository.saveInFile()), response.getOutputStream());
            response.flushBuffer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @PostMapping(value = "/load", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String loadPersons(@RequestBody MultipartFile file){
        return personRepository.loadFromFile(file);
    }
}
