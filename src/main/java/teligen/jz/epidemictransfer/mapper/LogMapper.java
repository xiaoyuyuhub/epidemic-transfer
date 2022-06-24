package teligen.jz.epidemictransfer.mapper;

import cn.hutool.core.bean.DynaBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import teligen.jz.epidemictransfer.entity.LogEntity;

@Mapper
public interface LogMapper extends BaseMapper<DynaBean> {
}
