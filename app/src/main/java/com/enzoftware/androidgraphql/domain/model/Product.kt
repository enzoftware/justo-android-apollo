package com.enzoftware.androidgraphql.domain.model

import com.enzoftware.android.graphql.FetchProductsQuery
import com.squareup.moshi.JsonClass


/**
 * Created by Enzo Lizama Paredes on 12/1/20.
 * Contact: lizama.enzo@gmail.com
 */
@JsonClass(generateAdapter = true)
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val image: String?
)


fun FetchProductsQuery.Products.toProducts() = edges.map { it.toProduct() }

fun FetchProductsQuery.Edge.toProduct() = Product(
    id = node.id,
    name = node.name,
    description = node.description,
    image = node.thumbnail?.url
)