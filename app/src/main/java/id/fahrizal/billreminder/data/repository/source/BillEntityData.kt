package id.fahrizal.billreminder.data.repository.source

import id.fahrizal.billreminder.data.model.Bill

interface BillEntityData {

    suspend fun save(bill: Bill)  : Long
    suspend fun get(): List<Bill>
}