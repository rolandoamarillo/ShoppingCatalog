package com.rolandoamarillo.shoppingcatalog.model

import android.os.Parcel
import android.os.Parcelable
import com.rolandoamarillo.shoppingcatalog.api.response.ItemResponse
import com.rolandoamarillo.shoppingcatalog.ui.adapters.base.BasePagingAdapter.Companion.ITEM

/**
 * Class representing an item from a search query
 */
class ShopItem(
    val id: String,
    val title: String,
    val price: Long,
    val currencyId: String,
    val thumbnail: String
) : ViewType(), Parcelable {


    companion object {
        /**
         * Convert method from the raw response object
         */
        fun fromItemResponse(itemResponse: ItemResponse): ShopItem {
            return ShopItem(
                itemResponse.id,
                itemResponse.title,
                itemResponse.price,
                itemResponse.currencyId,
                itemResponse.thumbnail
            )
        }

        @JvmField
        val CREATOR = object : Parcelable.Creator<ShopItem> {
            override fun createFromParcel(parcel: Parcel): ShopItem {
                return ShopItem(parcel)
            }

            override fun newArray(size: Int): Array<ShopItem?> {
                return arrayOfNulls(size)
            }
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun getViewType(): Int = ITEM
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeLong(price)
        parcel.writeString(currencyId)
        parcel.writeString(thumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }
}