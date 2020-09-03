package com.peranidze.products.domain.mapper

interface Mapper<Dto, Entity, Domain> {

    fun fromDtoToDomain(dto: Dto): Domain

    fun fromDtoListToDomainList(dtoList: List<Dto>) = dtoList.map { fromDtoToDomain(it) }

    fun fromDomainToEntity(domain: Domain): Entity

    fun fromDomainListToEntityList(domainList: List<Domain>) =
        domainList.map { fromDomainToEntity(it) }

    fun fromEntityToDomain(entity: Entity): Domain

    fun fromEntityListToDomainList(entityList: List<Entity>) =
        entityList.map { fromEntityToDomain(it) }
}
