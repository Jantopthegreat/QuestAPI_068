package com.example.pertemuan13.ui.views


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan13.R
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.ui.PenyediaViewModel
import com.example.pertemuan13.ui.customwidget.TopAppBar
import com.example.pertemuan13.ui.navigation.DestinasiNavigasi
import com.example.pertemuan13.ui.viewmodel.HomeMhsUiState
import com.example.pertemuan13.ui.viewmodel.HomeMhsViewModel


object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Daftar Mahasiswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onEditClick: (String) -> Unit = {},
    viewModel: HomeMhsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getMahasiswa()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Mahasiswa")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            homeMhsUiState = viewModel.mhsUiState,
            retryAction = { viewModel.getMahasiswa() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onEditClick = onEditClick,
            onDeleteClick = {
                viewModel.deleteMhs(it.nim)
                viewModel.getMahasiswa()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeMhsUiState: HomeMhsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {},
    onDetailClick: (String) -> Unit,
    onEditClick: (String) -> Unit,
) {
    when (homeMhsUiState) {
        is HomeMhsUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeMhsUiState.Success -> {
            if (homeMhsUiState.mahasiswa.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Mahasiswa")
                }
            } else {
                MhsLayout(
                    mahasiswa = homeMhsUiState.mahasiswa,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.nim) },
                    onEditClick = { onEditClick(it.nim) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeMhsUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(20.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.con_error),
            contentDescription = "",
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun MhsLayout(
    mahasiswa: List<Mahasiswa>,
    modifier: Modifier = Modifier,
    onDetailClick: (Mahasiswa) -> Unit,
    onDeleteClick: (Mahasiswa) -> Unit = {},
    onEditClick: (Mahasiswa) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(mahasiswa) { mahasiswa ->
            MhsCard(
                mahasiswa = mahasiswa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(mahasiswa) },
                onDeleteClick = {
                    onDeleteClick(mahasiswa)

                }
                ,
                onEditClick = {
                    onEditClick(mahasiswa)
                }

            )
        }
    }
}

@Composable
fun MhsCard(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {},
    onEditClick: (Mahasiswa) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mahasiswa.nama,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onEditClick(mahasiswa) }) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = null,
                        Modifier.padding(3.dp)
                    )
                }
                    IconButton(onClick = { onDeleteClick(mahasiswa) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                        )
                    }
                    Text(
                        text = mahasiswa.nim,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Text(
                    text = mahasiswa.kelas,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = mahasiswa.alamat,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
}