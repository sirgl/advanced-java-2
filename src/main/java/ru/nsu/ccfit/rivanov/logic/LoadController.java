package ru.nsu.ccfit.rivanov.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.rivanov.jpa.NodeRepository;
import ru.nsu.ccfit.rivanov.services.NodeDao;
import ru.nsu.ccfit.rivanov.services.NodeSupplier;

import java.util.stream.Stream;

@Component
public class LoadController {
    @Autowired
    private NodeSupplier nodeSupplier;

    @Autowired
    private NodeRepository nodeDao;

    @Autowired
    private NodeDao dao;

    public void loadToDatabase() {
        Stream.generate(nodeSupplier::nextNode)
                .limit(5000000)
                .forEach(dao::createNode);

//        Node one = nodeDao.findOne(new BigInteger("1"));
//        List<Tag> tag = one.getTag();
//        Tag[] tags = tag.toArray(new Tag[0]);
//        int x= 3;
    }
}
