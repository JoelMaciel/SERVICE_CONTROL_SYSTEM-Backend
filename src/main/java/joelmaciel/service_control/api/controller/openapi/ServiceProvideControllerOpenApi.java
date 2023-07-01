package joelmaciel.service_control.api.controller.openapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import joelmaciel.service_control.api.dto.ServiceProvidedDTO;
import joelmaciel.service_control.api.dto.request.ServiceProvidedRequestDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Service Provided")
public interface ServiceProvideControllerOpenApi {

    @ApiOperation("Search all ServiceProvided - Example :  Enter name = 'Joel' and month = 6 ")
     List<ServiceProvidedDTO> getAll(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                           @RequestParam(value = "month", required = false) Integer month);
    @ApiOperation("Save a ServiceProvided")
    ServiceProvidedDTO save(@RequestBody @Valid ServiceProvidedRequestDTO serviceProvidedRequestDTO) ;
}
