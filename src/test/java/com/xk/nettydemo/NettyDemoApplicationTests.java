package com.xk.nettydemo;

import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import com.xk.nettydemo.bean.MapDtoModel;
import com.xk.nettydemo.bean.PersonModel;
import com.xk.nettydemo.bean.Test2;
import com.xk.nettydemo.bean.TestDtoModel;
import com.xk.nettydemo.util.SerializationUtils;
import com.xk.nettydemo.util.Serializer;
import com.xk.nettydemo.util.kryoSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class NettyDemoApplicationTests {

    @Test
    public void contextLoads() throws InvalidProtocolBufferException
    {
        PersonModel.Person build = PersonModel.Person.newBuilder().setEmail("Asdad@qqwd.cxcl").setId(1).setName("分公司答复").build();
        byte[] bytes = build.toByteArray();
        System.out.println(bytes.length);
        PersonModel.Person person = PersonModel.Person.parseFrom(bytes);
        long l = System.nanoTime();
        PersonModel.Person.parseFrom( build.toByteArray());
        long l2 = System.nanoTime();
        System.out.println(person.getEmail());
        System.out.println(person.getName());
        System.out.println(l2-l);
    }


    @Test
    public void contextLoads2() throws InvalidProtocolBufferException
    {
        TestDtoModel.TestDto.Builder builder = TestDtoModel.TestDto.newBuilder().setBoolean(true).setDoub(1.22).setId(1).setName("123").addPhones(TestDtoModel.TestDto.PhoneNumber.newBuilder().setNumber("12312312").build());

        for (int i=0;i<100;i++){
            builder.addPhones(TestDtoModel.TestDto.PhoneNumber.newBuilder().setNumber("12312312"+i).build());
        }

        TestDtoModel.TestDto build = builder.build();
        log.info("build:{}",build.toString());

        log.info("size:{}",build.toByteArray().length);

//        log.info("ss:{}",TestDtoModel.TestDto.parseFrom(build.toByteArray()).toString());



    }

    @Test
    public void test() throws JsonFormat.ParseException
    {
        MapDtoModel.MaptDto build = MapDtoModel.MaptDto.newBuilder().putMap("12", "123")
                .putMapList("aa", MapDtoModel.MaptDto.list.newBuilder()
                        .addEle(MapDtoModel.MaptDto.Result.newBuilder()
                                .setUrl("http://www.www")
                                .setTitle("2222222222")
                                .addSnippets("AAAAAAAA")
                                .addSnippets("BBBBBBBBBB")
                                .build())
                        .build()).build();
        log.info("size:{}",build.toBuilder().toString());

        log.info("",build.getMapMap().toString());
        log.info(build.getMapListMap().toString());
        build.getMapListMap().values().forEach(v->v.getEleList().stream().forEach(result -> result.getSnippetsList().forEach(s -> log.info(s))));

        String s = JsonFormat.printToString(build);
        log.info("json:{}",s);

        MapDtoModel.MaptDto.Builder builder = MapDtoModel.MaptDto.newBuilder();
        JsonFormat.merge(s,builder);

        log.info("builder:{}",builder.toString());

    }

    @Test
    public void test2()
    {
        Test2 test2=new Test2();
        test2.setEmail("Asdad@qqwd.cxcl");
        test2.setId(1);
        test2.setName("分公司答复");
        byte[] serialize = SerializationUtils.serialize(test2);
        log.info(serialize.length+"");

        Test2 deserialize = SerializationUtils.deserialize(serialize, Test2.class);

        long l = System.nanoTime();
        byte[] serialize2 = SerializationUtils.serialize(deserialize);
        System.out.println(serialize2.length);
        Test2 deserialize1 = SerializationUtils.deserialize(serialize2, Test2.class);
        long l2 = System.nanoTime();
        log.info(deserialize1.toString());
        log.info("{}",l2-l);

    }

    @Test
    public void test3()
    {
        Test2 test2=new Test2();
        test2.setEmail("Asdad@qqwd.cxcl");
        test2.setId(1);
        test2.setName("分公司答复");
        Serializer serializer=new kryoSerializer(Test2.class);
        byte[] serialize = serializer.serialize(test2);
        log.info(serialize.length+"");

        Test2 deserialize = null;
        deserialize=serializer.deserialize(serialize);


        long l = System.nanoTime();
        byte[] serialize2 = serializer.serialize(test2);
        System.out.println(serialize2.length);
        Test2 deserialize1 = serializer.deserialize(serialize);
        long l2 = System.nanoTime();
        log.info(deserialize1.toString());
        log.info("{}",l2-l);

    }
}

