package ru.nsu.ccfit.rivanov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ccfit.rivanov.entities.Node;
import ru.nsu.ccfit.rivanov.jpa.NodeRepository;

import java.math.BigInteger;
import java.util.List;

@RestController("nodes")
public class RestNodeController {
    @Autowired
    private NodeRepository repository;

    @RequestMapping(value = "/{nodeId}", method = RequestMethod.GET)
    public ResponseEntity<Node> getNode(@PathVariable("nodeId") BigInteger id) {
        if(repository.exists(id)) {
            return new ResponseEntity<>(repository.findOne(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Node searchNodes(@RequestParam(name = "name") String name,
                            @RequestParam(name = "value") String value) {
        // TODO
        if(name != null){

        }
        return null;
    }

    @RequestMapping(value = "/nodes", method = RequestMethod.POST)
    public ResponseEntity<BigInteger> saveNode(Node node) {
        Node save = repository.save(node);
        return new ResponseEntity<>(save.getId(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{nodeId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteNode(@PathVariable("nodeId") BigInteger id) {
        if(repository.exists(id)) {
            repository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Node does not exists", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{nodeId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateNode(@RequestBody Node node,
                           @PathVariable BigInteger nodeId) {
        if(!repository.exists(nodeId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        node.setId(nodeId);
        repository.save(node);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/near", method = RequestMethod.GET)
    public List<Node> findNodesNearPoint(@RequestParam Double lat,
                                         @RequestParam Double lon,
                                         @RequestParam Double distance) {
        return repository.findNodeNearPoint(lat, lon, distance);
    }
}
