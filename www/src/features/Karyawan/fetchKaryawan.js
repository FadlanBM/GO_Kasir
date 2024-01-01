import axiosInstance from "@/lib/axios";
import { useQuery } from "@tanstack/react-query";

export const GetKaryawan = () => {
  const { data, isLoading } = useQuery({
    queryFn: async () => {
      const karyawanResponse = await axiosInstance.get("/api/karyawan");

      return karyawanResponse;
    },
  });

  return {
    data: data,
    isLoading: isLoading,
  };
};
