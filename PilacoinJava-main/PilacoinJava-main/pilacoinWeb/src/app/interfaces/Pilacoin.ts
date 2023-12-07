import { Transacoes } from "./Transacoes"

export interface Pilacoin {

    id?: number
    dataCriacao?: Date
    chaveCriador?: BinaryData
    nomeCriador?: string
    status?: string
    nonce?: string
    transacoes?: Transacoes

}