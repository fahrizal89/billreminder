package id.fahrizal.billreminder.data.repository

import id.fahrizal.billreminder.domain.model.Bill

interface BillRepository {

    suspend fun save(bill: Bill)
    suspend fun get(): List<Bill>
}