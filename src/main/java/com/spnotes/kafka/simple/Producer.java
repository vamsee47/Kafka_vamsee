package com.spnotes.kafka.simple;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

/**
 * Created by VamseeKrishna_Gangapatnam on 10/28/19.
 */
public class Producer {

    private static Scanner in;

    public static void main(String[] argv) throws Exception {
        if (argv.length != 1) {
            System.err.println("Please specify 1 parameters ");
            System.exit(-1);
        }
        String topicName = argv[0];
        in = new Scanner(System.in);
        System.out.println("-------------------------------");
        System.out.println("Let's Generate random string");
        System.out.println("-------------------------------");
        System.out.println("Enter message(type exit to quit)");
        
        //Configure the Producer
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        org.apache.kafka.clients.producer.Producer producer = new KafkaProducer(configProperties);
       int counter = 1;
        while (counter < 11) {
            //TODO: Make sure to use the ProducerRecord constructor that does not take parition Id
            String str =  randomAlphaNumeric(counter);
            System.out.println("Length"+counter+"\nGenerated String : " + str);
            ProducerRecord<String, String> rec = new ProducerRecord<String, String>(topicName, str);
            producer.send(rec);
           counter++;
        }
        in.close();
        producer.close();
    }

    private static final String ALPHA_NUMERIC_STRING = "Bearcat is always a bearcat";

    public static String randomAlphaNumeric(int count) {
      
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}