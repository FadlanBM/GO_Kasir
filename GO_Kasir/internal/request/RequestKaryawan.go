package request

type RegisterKaryawanRequest struct {
	Nik          string `json:"nik"`
	Nama_Petugas string `json:"nama_petugas"`
	Username     string `json:"username"`
	Password     string `json:"password"`
	TokoID       uint   `json:"toko_id"`
	Telp         string `json:"telp"`
}

type UpdateKaryawanRequest struct {
	Nik          string `json:"nik"`
	Nama_Petugas string `json:"nama_petugas"`
	Username     string `json:"username"`
	TokoID       uint   `json:"toko_id"`
	Telp         string `json:"telp"`
}
