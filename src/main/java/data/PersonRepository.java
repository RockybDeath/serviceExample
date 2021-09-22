package data;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class PersonRepository {
    private Map<Integer,Person> persons = Collections.synchronizedMap(new TreeMap<>());
    private long default_timeout;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread th = new Thread(r);
            th.setDaemon(true);
            return th;
        }
    });
    public PersonRepository(long default_timeout){
        this.default_timeout = default_timeout;
        scheduler.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                long current = System.currentTimeMillis();
                for(Integer k: persons.keySet()){
                    if(!persons.get(k).isLive(current)){
                        persons.remove(k);
                    }
                }
            }
        }, 1, default_timeout/5, TimeUnit.MILLISECONDS);
    }

    public void setDefault_timeout(long default_timeout) {
        this.default_timeout = default_timeout;
    }

    public String getAll(){
        StringBuilder builder = new StringBuilder();
        for(Integer i: persons.keySet()){
            builder.append(persons.get(i).toString()).append("\n");
        }
        return builder.toString();
    }
    public String getById(int id){
        return persons.get(id).toString();
    }
    public String add(int id, Person person){
        if(persons.containsKey(id)){
            this.persons.replace(id, person);
        } else this.persons.put(id, person);
        return "Объект добавлен успешно";
    }
    public String removeById(int id){
        if(persons.containsKey(id)) {
            return "Data delete successfully - "+persons.remove(id).toString();
        }
        else return "Такого объекта нет";
    }
    public File saveInFile(){
        try {
            File file = new File("dump.bin");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(persons);
            out.flush();
            out.close();
            return file;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public String loadFromFile(MultipartFile file){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(file.getBytes()));
            Map<Integer,Person> personMap = (Map<Integer, Person>) objectInputStream.readObject();
            this.persons = personMap;
            return "Загрузка прошла успешно";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}
