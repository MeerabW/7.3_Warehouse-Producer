package at.ac.tgm.mwali.warehouseproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import at.ac.tgm.mwali.shared.WarehouseData;

@RestController
public class WarehouseProducer {

    @Autowired
    private KafkaTemplate<String, WarehouseData> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(WarehouseProducer.class);

    @PostMapping("/send")
    public ResponseEntity<WarehouseData> sendWarehouse(@RequestBody WarehouseData warehouse) {
        if (warehouse.getWarehouseID() == null) {
            return ResponseEntity.badRequest().body(warehouse);
        }

        kafkaTemplate.send("warehouse-001", warehouse.getWarehouseID(), warehouse);
        log.info("WAREHOUSE 001 Data Sent: {}", warehouse.getWarehouseID());
        return ResponseEntity.ok(warehouse);
    }

}