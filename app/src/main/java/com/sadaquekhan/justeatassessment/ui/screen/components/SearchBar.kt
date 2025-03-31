package com.sadaquekhan.justeatassessment.ui.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun SearchBar(
    postcode: String,
    onPostcodeChange: (String) -> Unit,
    onSearch: () -> Unit,
    showError: Boolean
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = postcode,
        onValueChange = onPostcodeChange,
        label = { Text("Enter UK postcode") },
        isError = showError,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onSearch()
            }
        )
    )
}