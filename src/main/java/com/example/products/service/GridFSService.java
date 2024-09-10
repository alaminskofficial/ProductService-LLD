package com.example.products.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class GridFSService {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    public String storeFile(MultipartFile file) throws IOException {
        return gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType()).toString();
    }

    public InputStreamResource getFile(String fileId) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(query(where("_id").is(fileId)));
        return new InputStreamResource(gridFsTemplate.getResource(gridFSFile).getInputStream());
    }
}

