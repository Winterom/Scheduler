package alexey.grizly.com.properties.services;


import alexey.grizly.com.properties.models.PropertiesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropertiesService {
    private final PropertiesConverter propertiesConverter;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PropertiesService(PropertiesConverter propertiesConverter, NamedParameterJdbcTemplate jdbcTemplate) {
        this.propertiesConverter = propertiesConverter;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Object getProperty(Class<?> clazz){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("clazz",clazz.getName());
        PropertiesModel propertiesModel = jdbcTemplate.queryForObject("SELECT clazz,property FROM properties where clazz=:clazz",param, new BeanPropertyRowMapper<>(PropertiesModel.class));
        return propertiesConverter.convertFromJson(propertiesModel.getProperty(),clazz);
    }

    @Transactional
    public boolean updateProperty(PropertiesModel model){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("clazz",model.getClazz());
        param.addValue("property",model.getProperty());
        int countUpdateRow =jdbcTemplate.update("UPDATE properties SET property=:property::jsonb WHERE clazz=:clazz",param);
        return countUpdateRow == 1;
    }

}
