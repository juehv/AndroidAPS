package info.nightscout.androidaps.database.daos

import androidx.room.Dao
import androidx.room.Query
import info.nightscout.androidaps.database.TABLE_EFFECTIVE_PROFILE_SWITCHES
import info.nightscout.androidaps.database.entities.EffectiveProfileSwitch

@Suppress("FunctionName")
@Dao
abstract class EffectiveProfileSwitchDao : BaseDao<EffectiveProfileSwitch>() {

    @Query("SELECT * FROM $TABLE_EFFECTIVE_PROFILE_SWITCHES WHERE id = :id")
    abstract override fun findById(id: Long): EffectiveProfileSwitch?

    @Query("DELETE FROM $TABLE_EFFECTIVE_PROFILE_SWITCHES")
    abstract override fun deleteAllEntries()
}