package teligen.jz.epidemictransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class EpidemictransferJobService {

    @Autowired
    GetFtpFileService getFtpFileService;

    @Autowired
    UnzipService unzipService;

    @PostConstruct
    public void transferJobTask() {

        getFtpFileService.getFtpFileJob();
        unzipService.unzipJob();

    }
}
