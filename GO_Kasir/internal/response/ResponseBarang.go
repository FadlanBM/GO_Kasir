package response

type BarangResponse struct {
	ID         uint   `json:"ID"`
	Name       string `json:"name"`
	CodeBarang string `json:"code_barang"`
	Merek      string `json:"merek"`
	Tipe       string `json:"tipe"`
	Price      string `json:"price"`
	Stock      int    `json:"stock"`
}
