package com.benjdero.gameoflife.ui.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Speed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.benjdero.gameoflife.ui.theme.MyTheme

@Composable
fun TestBtnView(
    icon: ImageVector,
    text: String
) {
    Card {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(
                    start = 8.dp
                )
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = icon,
                    contentDescription = null
                )
                Text(
                    text = text
                )
            }
            Column {
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun TestBtnView2(
    icon: ImageVector,
    text: String
) {
    Box(
        modifier = Modifier.background(
            shape = MaterialTheme.shapes.medium,
            brush = Brush.linearGradient(
                0.00f to Color(0xFFF2F6F8),
                0.50f to Color(0xFFd8e1e7),
                0.51f to Color(0xFFb5c6d0),
                1.00f to Color(0xFFe0eff9),
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            )
        )
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(
                    start = 16.dp,
                    end = 8.dp
                )
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF222222)
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF222222)
                )
            }
            Column {
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color(0xFF222222)
                    )
                }
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Default.Remove,
                        contentDescription = null,
                        tint = Color(0xFF222222)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TestBtnPreview() {
    MyTheme {
        Row {
            Column {
                TestBtnView(
                    icon = Icons.Default.Search,
                    text = "100%"
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                TestBtnView(
                    icon = Icons.TwoTone.Speed,
                    text = "x4"
                )
            }
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Column {
                TestBtnView2(
                    icon = Icons.Default.Search,
                    text = "100%"
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                TestBtnView2(
                    icon = Icons.TwoTone.Speed,
                    text = "x4"
                )
            }
        }
    }
}
