package com.richaa2.mappdp.presentation.addLocation

data class AddLocationFormState(
        val title: String = "",
        val description: String = "",
        val image: ByteArray? = null,
        val titleError: String? = null,
        val descriptionError: String? = null
    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
//        if (javaClass != other?.javaClass) return false

        other as AddLocationFormState

        if (title != other.title) return false
        if (description != other.description) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (titleError != other.titleError) return false
        if (descriptionError != other.descriptionError) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + (titleError?.hashCode() ?: 0)
        result = 31 * result + (descriptionError?.hashCode() ?: 0)
        return result
    }
}