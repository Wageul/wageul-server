package com.wageul.wageul_server.s3_image.service.port;

import java.util.List;

import com.wageul.wageul_server.experience.domain.Experience;
import com.wageul.wageul_server.s3_image.domain.ExImage;

public interface ExImageRepository extends ExImageCustomRepository {

	ExImage save(ExImage exImage);
}
