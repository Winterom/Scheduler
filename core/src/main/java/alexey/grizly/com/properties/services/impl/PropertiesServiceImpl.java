package alexey.grizly.com.properties.services.impl;


import alexey.grizly.com.properties.models.PropertiesModel;
import alexey.grizly.com.properties.services.PropertiesConverter;
import alexey.grizly.com.properties.services.PropertiesService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropertiesServiceImpl implements PropertiesService {
    private final PropertiesConverter propertiesConverter;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PropertiesServiceImpl(PropertiesConverter propertiesConverter, NamedParameterJdbcTemplate jdbcTemplate) {
        this.propertiesConverter = propertiesConverter;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    @Nullable
    public Object getProperty(Class<?> clazz){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("clazz",clazz.getName());
        PropertiesModel propertiesModel = jdbcTemplate.queryForObject("SELECT clazz,property FROM properties where clazz=:clazz",param, new BeanPropertyRowMapper<>(PropertiesModel.class));
        if(propertiesModel==null){
            return null;
        }
        return propertiesConverter.convertFromJson(propertiesModel.getProperty(),clazz);
    }

    @Override
    @Transactional
    public boolean updateProperty(PropertiesModel model){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("clazz",model.getClazz());
        param.addValue("property",model.getProperty());
        int countUpdateRow =jdbcTemplate.update("UPDATE properties SET property=:property::jsonb WHERE clazz=:clazz",param);
        return countUpdateRow == 1;
    }

}
