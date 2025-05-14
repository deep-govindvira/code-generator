import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GGGGGGGGGGMapperConfig {

    @Autowired
    public GGGGGGGGGGMapperConfig(ModelMapper modelMapper) {
        TypeMap<GGGGGGGGGG, GGGGGGGGGGDTO> SLLLLLLLLLGGGGGGGGGGDTOTypeMap = modelMapper.createTypeMap(GGGGGGGGGG.class, GGGGGGGGGGDTO.class);
        TypeMap<GGGGGGGGGGDTO, GGGGGGGGGG> SLLLLLLLLLDTOGGGGGGGGGGTypeMap = modelMapper.createTypeMap(GGGGGGGGGGDTO.class, GGGGGGGGGG.class);
        