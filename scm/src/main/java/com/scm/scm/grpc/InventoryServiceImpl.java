package com.scm.scm.grpc;

import com.scm.scm.grpc.InventoryServiceOuterClass.InventoryUpdateResponse;
import com.scm.scm.grpc.InventoryServiceOuterClass.ProductRequest;
import com.scm.scm.grpc.InventoryServiceOuterClass.ProductResponse;
import com.scm.scm.grpc.InventoryServiceOuterClass.UpdateInventoryRequest;
import com.scm.scm.model.Product;
import com.scm.scm.util.InventoryDataUtil;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@GrpcService
public class InventoryServiceImpl extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final InventoryDataUtil inventoryDataUtil;

    public InventoryServiceImpl(InventoryDataUtil inventoryDataUtil) {
        this.inventoryDataUtil = inventoryDataUtil;
    }

}
