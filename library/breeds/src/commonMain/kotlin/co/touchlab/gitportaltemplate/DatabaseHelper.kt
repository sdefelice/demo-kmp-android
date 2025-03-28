package co.touchlab.gitportaltemplate

import app.cash.sqldelight.coroutines.asFlow
import co.touchlab.gitportaltemplate.db.Breed
import co.touchlab.gitportaltemplate.db.GitPortalTemplateDb
import co.touchlab.gitportaltemplate.sqldelight.transactionWithContext
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Hello from Android!
 */
internal class DatabaseHelper(
    sqlDriver: SqlDriver,
    private val breedAnalytics: BreedAnalytics,
    private val backgroundDispatcher: CoroutineDispatcher
) {
    private val dbRef: GitPortalTemplateDb = GitPortalTemplateDb(sqlDriver)

    fun selectAllItems(): Flow<List<Breed>> =
        dbRef.tableQueries
            .selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .flowOn(backgroundDispatcher)

    suspend fun insertBreeds(breeds: List<String>) {
        breedAnalytics.insertingBreedsToDatabase(breeds.size)
        dbRef.transactionWithContext(backgroundDispatcher) {
            breeds.forEach { breed ->
                dbRef.tableQueries.insertBreed(breed)
            }
        }
    }

    fun selectById(id: Long): Flow<List<Breed>> =
        dbRef.tableQueries
            .selectById(id)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .flowOn(backgroundDispatcher)

    suspend fun deleteAll() {
        breedAnalytics.databaseCleared()
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.tableQueries.deleteAll()
        }
    }

    suspend fun updateFavorite(breedId: Long, favorite: Boolean) {
        breedAnalytics.favoriteSaved(id = breedId, favorite = favorite)
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.tableQueries.updateFavorite(favorite, breedId)
        }
    }
}
