package com.peranidze.products.factory

import com.peranidze.products.domain.model.SalePrice
import com.peranidze.products.factory.DataFactory.Factory.randomDouble
import com.peranidze.products.factory.DataFactory.Factory.randomUuid
import com.peranidze.products.remote.model.SalePriceDto

object SalePriceFactory {

    fun makeSalePriceDto(): SalePriceDto = SalePriceDto(randomDouble(), randomUuid())

    fun makeSalePriceModel(): SalePrice = SalePrice(randomDouble(), randomUuid())
}
