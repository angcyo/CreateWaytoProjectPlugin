package ${packageName}

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/23
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
@Entity
data class PlaceholderEntity(
    @Id var entity_id: Long = 0L
)