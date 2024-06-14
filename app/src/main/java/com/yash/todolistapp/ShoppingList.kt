package com.yash.todolistapp

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun List()
{
    var sItems by remember { mutableStateOf(listOf<Item>()) }
    var showdialogue by remember { mutableStateOf(false) }
    var ItemName by remember { mutableStateOf("") }
    var ItemQty by remember { mutableStateOf("1") }
    Column (modifier= Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Button(onClick = {showdialogue=true})
        {
            Text(text = "Add Item")
        }
        LazyColumn(modifier= Modifier
            .fillMaxSize()
            .padding(16.dp))
        {
            items(sItems) {
                item->
                if (item.isEditing)
                {
                    shoppingItemEditor(item = item, oneEditComplete =
                    {
                        editedName,editedQuantity ->sItems=sItems.map{it.copy(isEditing = false)}
                        val editedItem=sItems.find{it.id==item.id}
                        editedItem?.let{
                            it.name=editedName
                            it.Qty=editedQuantity

                    }
                    })

                } else
                {
                    shoppingListItem(item=item,
                        onEditClick ={sItems=sItems.map{it.copy(isEditing = it.id==item.id)}},
                        onDeleteClick ={
                       sItems= sItems-item
                    })
                }

            }

        }

    }
    if(showdialogue)
    {
        AlertDialog(onDismissRequest = { showdialogue=false },
            confirmButton =
        {
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalArrangement =Arrangement.SpaceBetween) {
            Button(onClick = {
                if(ItemName.isNotBlank())
                {
                    val newItem=Item(id=sItems.size+1,name=ItemName,Qty=ItemQty.toInt())
                    sItems=sItems+newItem
                    ItemName=""
                    ItemQty=""
                }

            }) {
                Text(text = "Add")
            }
            Button(onClick = {showdialogue=false }) {
                Text(text = "Cancel")
            }

        }



        },
            title ={ Text(text="Add Item")},
            text = {
                Column {
                OutlinedTextField(value =ItemName,
                    onValueChange ={ItemName=it},
                    singleLine = true)
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value =ItemQty,
                        onValueChange ={ItemQty=it},
                        singleLine = true)
            }

        })

     }
    }
data class Item(val id:Int , var name:String, var Qty:Int, var isEditing:Boolean=false)
@Preview
@Composable
fun ListPreview()
{
    List()
}
@Composable
fun shoppingItemEditor(item:Item , oneEditComplete:(String,Int)->Unit)
{
    var editedName by remember { mutableStateOf(item.name) }
    var editedQty by remember { mutableStateOf(item.Qty.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        Column(modifier= Modifier
            .padding(8.dp)
            .background(Color.White)) {
            BasicTextField(value = editedName, onValueChange = {editedName=it}, singleLine = true,modifier= Modifier
                .wrapContentSize()
                .padding(8.dp))

            BasicTextField(value = editedQty, onValueChange = {editedQty=it}, singleLine = true,modifier= Modifier
                .wrapContentSize()
                .padding(8.dp))
        }
        Button(onClick = { isEditing=false
        oneEditComplete(editedName,editedQty.toIntOrNull()?:1)})
        {
            Text(text = "Save")

        }

    }

}
@Composable
fun shoppingListItem(
    item: Item,
    onEditClick:()->Unit,
    onDeleteClick:()->Unit
)
{
    Row(modifier= Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .border(border = BorderStroke(2.dp, Color.Cyan), shape = RoundedCornerShape(20)), horizontalArrangement =Arrangement.SpaceBetween)
    {
        Text(text = item.name,modifier=Modifier.padding(8.dp))
        Text(text = "Qty:${item.Qty}",modifier=Modifier.padding(8.dp))
        Row(modifier=Modifier.padding(8.dp))
        {
            IconButton(onClick =onEditClick)
            {
              androidx.compose.material3.Icon(imageVector = Icons.Default.Edit,contentDescription = null)

            }
            IconButton(onClick =onDeleteClick)
            {
                androidx.compose.material3.Icon(imageVector = Icons.Default.Delete,contentDescription = null)

            }
        }
        }
}


