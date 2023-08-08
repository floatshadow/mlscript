(module 
  (import "system" "mem" (memory 100))
  (func $logI32 (import "system" "logI32") (param i32 i32))
  (func $logF64 (import "system" "logF64") (param f64))
  (global (mut i32) i32.const 0) 
  (export "main_0" (func $main_0))
  (func $main_0 (local $f i32)(local $a i32)(local $g1 i32)(local $x1 i32)(local $d i32)
    block $entry
      i32.const 1
      i32.const 1
      i32.eq
      if
        i32.const 1
        local.set $f
        local.get $f
        local.set $a
      else
        i32.const 0
        local.set $d
        local.get $d
        local.set $a
      end
    end
    local.get $a
    local.set $x1
    local.get $x1
    i32.const 2
    call $logI32
    i32.const 0
    local.set $g1
  )
  (export "main_1" (func $main_1))
  (func $main_1 (local $n i32)(local $f i32)(local $a i32)(local $m i32)(local $x1 i32)(local $h i32)(local $q1 i32)(local $p1 i32)(local $k i32)(local $g i32)(local $y1 i32)(local $o i32)(local $d i32)
    block $entry
      i32.const 1
      i32.const 0
      i32.and
      local.set $g
      local.get $g
      i32.const 1
      i32.eq
      if
        i32.const 0
        local.set $f
        local.get $f
        local.set $a
      else
        i32.const 10
        local.set $d
        local.get $d
        local.set $a
      end
    end
    block $b
      local.get $a
      local.set $x1
      local.get $x1
      i32.const 10
      i32.add
      local.set $n
      local.get $n
      i32.const 10
      i32.eq
      local.set $o
      local.get $o
      i32.const 1
      i32.eq
      if
        i32.const 100
        local.set $m
        local.get $m
        local.set $h
      else
        i32.const 200
        local.set $k
        local.get $k
        local.set $h
      end
    end
    local.get $h
    local.set $y1
    local.get $x1
    i32.const 2
    call $logI32
    i32.const 0
    local.set $p1
    local.get $y1
    i32.const 2
    call $logI32
    i32.const 0
    local.set $q1
  )
  (export "main_2" (func $main_2))
  (func $main_2 (local $tmp01 i32)(local $e1 i32)(local $j i32)(local $n1 i32)(local $a i32)(local $m i32)(local $tmp0 i32)(local $g i32)(local $p i32)(local $h1 i32)(local $k1 i32)(local $q1 i32)(local $d i32)
    block $entry
      i32.const 51
      local.set $tmp0
      i32.const 51
      local.set $tmp01
      block $c
        block $i
          block $f
            block $l
              block $o
                block $Match_tmp01
                  local.get $tmp01
                  i32.const 52
                  i32.eq
                  i32.const 1
                  i32.mul
                  local.get $tmp01
                  i32.const 51
                  i32.eq
                  i32.const 2
                  i32.mul
                  i32.add
                  local.get $tmp01
                  i32.const 53
                  i32.eq
                  i32.const 3
                  i32.mul
                  i32.add
                  local.get $tmp01
                  i32.const 54
                  i32.eq
                  i32.const 4
                  i32.mul
                  i32.add
                  br_table 4 3 2 1 0
                end
                i32.const 54
                i32.const 2
                call $logI32
                i32.const 0
                local.set $q1
                local.get $q1
                local.set $p
                local.get $p
                local.set $a
                br $c
              end
              i32.const 53
              i32.const 2
              call $logI32
              i32.const 0
              local.set $n1
              local.get $n1
              local.set $m
              local.get $m
              local.set $a
              br $c
            end
            i32.const 51
            i32.const 2
            call $logI32
            i32.const 0
            local.set $h1
            local.get $h1
            local.set $g
            local.get $g
            local.set $a
            br $c
          end
          i32.const 52
          i32.const 2
          call $logI32
          i32.const 0
          local.set $k1
          local.get $k1
          local.set $j
          local.get $j
          local.set $a
          br $c
        end
        i32.const 55
        i32.const 2
        call $logI32
        i32.const 0
        local.set $e1
        local.get $e1
        local.set $d
        local.get $d
        local.set $a
      end
    end
  )
  (export "main_3" (func $main_3))
  (func $main_3 (local $n i32)(local $f1 i32)(local $a i32)(local $i1 i32)(local $b i32)(local $o1 i32)(local $x1 i32)(local $h i32)(local $k i32)(local $e i32)(local $l1 i32)
    block $entry
      global.get 0
      local.set $a
      global.get 0
      i32.const 2
      i32.store
      global.get 0
      i32.const 4
      i32.add
      global.set 0
      local.get $a
      local.set $x1
      block $d
        block $g
          block $j
            block $m
              block $Match_x1
                local.get $x1
                i32.load
                i32.const 0
                i32.eq
                i32.const 1
                i32.mul
                local.get $x1
                i32.load
                i32.const 1
                i32.eq
                i32.const 2
                i32.mul
                i32.add
                local.get $x1
                i32.load
                i32.const 2
                i32.eq
                i32.const 3
                i32.mul
                i32.add
                br_table 3 2 1 0
              end
              i32.const 30
              i32.const 2
              call $logI32
              i32.const 0
              local.set $o1
              local.get $o1
              local.set $n
              local.get $n
              local.set $b
              br $d
            end
            i32.const 20
            i32.const 2
            call $logI32
            i32.const 0
            local.set $l1
            local.get $l1
            local.set $k
            local.get $k
            local.set $b
            br $d
          end
          i32.const 10
          i32.const 2
          call $logI32
          i32.const 0
          local.set $i1
          local.get $i1
          local.set $h
          local.get $h
          local.set $b
          br $d
        end
        i32.const 40
        i32.const 2
        call $logI32
        i32.const 0
        local.set $f1
        local.get $f1
        local.set $e
        local.get $e
        local.set $b
      end
    end
  )
  (export "main_4" (func $main_4))
  (func $main_4 (local $f1 i32)(local $u i32)(local $a i32)(local $m1 i32)(local $v1 i32)(local $b i32)(local $s1 i32)(local $tmp1 i32)(local $h i32)(local $r i32)(local $p1 i32)(local $k i32)(local $o i32)(local $e i32)(local $l1 i32)
    block $entry
      global.get 0
      local.set $a
      global.get 0
      i32.const 0
      i32.store
      global.get 0
      i32.const 8
      i32.add
      global.set 0
      local.get $a
      i32.const 4
      i32.add
      i32.const 100
      i32.store
      local.get $a
      local.set $l1
      local.get $l1
      i32.load
      i32.const 0
      i32.eq
      if
        local.get $l1
        i32.const 4
        i32.add
        i32.load
        local.set $h
        local.get $h
        local.set $tmp1
        block $j
          block $t
            block $n
              block $q
                block $Match_tmp1
                  local.get $tmp1
                  i32.const 102
                  i32.eq
                  i32.const 1
                  i32.mul
                  local.get $tmp1
                  i32.const 100
                  i32.eq
                  i32.const 2
                  i32.mul
                  i32.add
                  local.get $tmp1
                  i32.const 101
                  i32.eq
                  i32.const 3
                  i32.mul
                  i32.add
                  br_table 3 2 1 0
                end
                i32.const 101
                i32.const 2
                call $logI32
                i32.const 0
                local.set $s1
                local.get $s1
                local.set $r
                local.get $r
                local.set $b
                br $j
              end
              i32.const 100
              i32.const 2
              call $logI32
              i32.const 0
              local.set $p1
              local.get $p1
              local.set $o
              local.get $o
              local.set $b
              br $j
            end
            i32.const 102
            i32.const 2
            call $logI32
            i32.const 0
            local.set $v1
            local.get $v1
            local.set $u
            local.get $u
            local.set $b
            br $j
          end
          i32.const 103
          i32.const 2
          call $logI32
          i32.const 0
          local.set $m1
          local.get $m1
          local.set $k
          local.get $k
          local.set $b
        end
      else
        i32.const 103
        i32.const 2
        call $logI32
        i32.const 0
        local.set $f1
        local.get $f1
        local.set $e
        local.get $e
        local.set $b
      end
    end
  )
  (export "main_5" (func $main_5))
  (func $main_5 (local $e i32)(local $tmp2 i32)(local $at1 i32)(local $ad1 i32)(local $y i32)(local $m1 i32)(local $i i32)(local $ah i32)(local $aq1 i32)(local $ak i32)(local $h i32)(local $length1 i32)(local $u1 i32)(local $t i32)(local $f1 i32)(local $a i32)(local $pos1 i32)(local $z1 i32)(local $as i32)(local $ap i32)(local $tmp3 i32)(local $q i32)(local $ac i32)(local $b i32)(local $r1 i32)(local $l i32)(local $al1 i32)(local $ai1 i32)
    block $entry
      global.get 0
      local.set $a
      global.get 0
      i32.const 0
      i32.store
      global.get 0
      i32.const 12
      i32.add
      global.set 0
      local.get $a
      i32.const 4
      i32.add
      i32.const 5
      i32.store
      local.get $a
      i32.const 8
      i32.add
      i32.const 12
      i32.store
      local.get $a
      local.set $pos1
      local.get $pos1
      i32.load
      i32.const 0
      i32.eq
      if
        local.get $pos1
        i32.const 4
        i32.add
        i32.load
        local.set $h
        local.get $h
        local.set $tmp2
        local.get $pos1
        i32.const 8
        i32.add
        i32.load
        local.set $i
        local.get $i
        local.set $tmp3
        block $k
          block $n
            block $am
              block $ae
                block $v
                  block $Match_tmp2
                    local.get $tmp2
                    i32.const 3
                    i32.eq
                    i32.const 1
                    i32.mul
                    local.get $tmp2
                    i32.const 12
                    i32.eq
                    i32.const 2
                    i32.mul
                    i32.add
                    local.get $tmp2
                    i32.const 5
                    i32.eq
                    i32.const 3
                    i32.mul
                    i32.add
                    local.get $tmp2
                    i32.const 4
                    i32.eq
                    i32.const 4
                    i32.mul
                    i32.add
                    br_table 4 3 2 1 0
                  end
                  local.get $tmp3
                  i32.const 3
                  i32.eq
                  if
                    i32.const 5
                    i32.const 2
                    call $logI32
                    i32.const 0
                    local.set $ad1
                    local.get $ad1
                    local.set $ac
                    local.get $ac
                    local.set $b
                  else
                    i32.const 1
                    i32.const 2
                    call $logI32
                    i32.const 0
                    local.set $z1
                    local.get $z1
                    local.set $y
                    local.get $y
                    local.set $b
                  end
                  br $k
                end
                local.get $tmp3
                i32.const 12
                i32.eq
                if
                  i32.const 13
                  i32.const 2
                  call $logI32
                  i32.const 0
                  local.set $al1
                  local.get $al1
                  local.set $ak
                  local.get $ak
                  local.set $b
                else
                  i32.const 1
                  i32.const 2
                  call $logI32
                  i32.const 0
                  local.set $ai1
                  local.get $ai1
                  local.set $ah
                  local.get $ah
                  local.set $b
                end
                br $k
              end
              local.get $tmp3
              i32.const 5
              i32.eq
              if
                i32.const 13
                i32.const 2
                call $logI32
                i32.const 0
                local.set $at1
                local.get $at1
                local.set $as
                local.get $as
                local.set $b
              else
                i32.const 1
                i32.const 2
                call $logI32
                i32.const 0
                local.set $aq1
                local.get $aq1
                local.set $ap
                local.get $ap
                local.set $b
              end
              br $k
            end
            local.get $tmp3
            i32.const 4
            i32.eq
            if
              i32.const 5
              i32.const 2
              call $logI32
              i32.const 0
              local.set $u1
              local.get $u1
              local.set $t
              local.get $t
              local.set $b
            else
              i32.const 1
              i32.const 2
              call $logI32
              i32.const 0
              local.set $r1
              local.get $r1
              local.set $q
              local.get $q
              local.set $b
            end
            br $k
          end
          i32.const 1
          i32.const 2
          call $logI32
          i32.const 0
          local.set $m1
          local.get $m1
          local.set $l
          local.get $l
          local.set $b
        end
      else
        i32.const 1
        i32.const 2
        call $logI32
        i32.const 0
        local.set $f1
        local.get $f1
        local.set $e
        local.get $e
        local.set $b
      end
    end
    local.get $b
    local.set $length1
  )
  (export "main_6" (func $main_6))
  (func $main_6 (local $u i32)(local $v1 i32)(local $i1 i32)(local $s1 i32)(local $x1 i32)(local $h i32)(local $r i32)(local $k i32)(local $o i32)(local $e i32)(local $l1 i32)
    i32.const 1
    local.set $x1
    local.get $x1
    i32.const 1
    i32.eq
    if
      block $m
        local.get $x1
        i32.const 1
        i32.eq
        if
          i32.const 10
          i32.const 2
          call $logI32
          i32.const 0
          local.set $v1
          local.get $v1
          local.set $u
          local.get $u
          local.set $o
        else
          i32.const 5
          i32.const 2
          call $logI32
          i32.const 0
          local.set $s1
          local.get $s1
          local.set $r
          local.get $r
          local.set $o
        end
      end
    else
      block $c
        local.get $x1
        i32.const 1
        i32.eq
        if
          i32.const -10
          i32.const 2
          call $logI32
          i32.const 0
          local.set $l1
          local.get $l1
          local.set $k
          local.get $k
          local.set $e
        else
          i32.const -5
          i32.const 2
          call $logI32
          i32.const 0
          local.set $i1
          local.get $i1
          local.set $h
          local.get $h
          local.set $e
        end
      end
    end
  )
  (export "main_7" (func $main_7))
  (func $main_7 (local $av1 i32)(local $tmp6 i32)(local $ay1 i32)(local $ah i32)(local $bn1 i32)(local $tmp61 i32)(local $bw1 i32)(local $e i32)(local $bf1 i32)(local $bs i32)(local $cj1 i32)(local $cr1 i32)(local $df1 i32)(local $ad1 i32)(local $ce1 i32)(local $dk1 i32)(local $tmp71 i32)(local $y i32)(local $br i32)(local $cw1 i32)(local $u1 i32)(local $t i32)(local $f1 i32)(local $bq i32)(local $cd i32)(local $a i32)(local $bj i32)(local $z1 i32)(local $ap i32)(local $cn i32)(local $m1 i32)(local $tmp7 i32)(local $af i32)(local $de i32)(local $bm i32)(local $i i32)(local $cv i32)(local $bk1 i32)(local $q i32)(local $ac i32)(local $b i32)(local $cq i32)(local $r1 i32)(local $l i32)(local $bp i32)(local $ag i32)(local $al1 i32)(local $dj i32)(local $tmp52 i32)(local $tmp62 i32)(local $tmp4 i32)(local $aq1 i32)(local $dm i32)(local $ak i32)(local $ax i32)(local $ci i32)(local $au i32)(local $bv i32)(local $be i32)(local $h i32)(local $tmp8 i32)(local $co1 i32)(local $tmp51 i32)(local $result1 i32)(local $dn1 i32)(local $tmp5 i32)
    block $entry
      global.get 0
      local.set $a
      global.get 0
      i32.const 0
      i32.store
      global.get 0
      i32.const 16
      i32.add
      global.set 0
      local.get $a
      i32.const 4
      i32.add
      i32.const 1
      i32.store
      local.get $a
      i32.const 8
      i32.add
      i32.const 2
      i32.store
      local.get $a
      i32.const 12
      i32.add
      i32.const 3
      i32.store
      local.get $a
      local.set $tmp4
      block $d
        block $g
          block $ae
            block $bo
              block $Match_tmp4
                local.get $tmp4
                i32.load
                i32.const 2
                i32.eq
                i32.const 1
                i32.mul
                local.get $tmp4
                i32.load
                i32.const 0
                i32.eq
                i32.const 2
                i32.mul
                i32.add
                local.get $tmp4
                i32.load
                i32.const 1
                i32.eq
                i32.const 3
                i32.mul
                i32.add
                br_table 3 2 1 0
              end
              local.get $tmp4
              i32.const 4
              i32.add
              i32.load
              local.set $bp
              local.get $bp
              local.set $tmp52
              local.get $tmp4
              i32.const 8
              i32.add
              i32.load
              local.set $bq
              local.get $bq
              local.set $tmp62
              local.get $tmp4
              i32.const 12
              i32.add
              i32.load
              local.set $br
              local.get $br
              local.set $tmp71
              local.get $tmp4
              i32.const 0
              i32.add
              i32.load
              local.set $bs
              local.get $bs
              local.set $tmp8
              block $bu
                block $bx
                  block $cs
                    block $Match_tmp52
                      local.get $tmp52
                      i32.const 0
                      i32.eq
                      i32.const 1
                      i32.mul
                      local.get $tmp52
                      i32.const 1
                      i32.eq
                      i32.const 2
                      i32.mul
                      i32.add
                      br_table 2 1 0
                    end
                    local.get $tmp62
                    i32.const 2
                    i32.eq
                    if
                      local.get $tmp71
                      i32.const 3
                      i32.eq
                      if
                        local.get $tmp8
                        i32.const 4
                        i32.eq
                        if
                          i32.const 5
                          i32.const 2
                          call $logI32
                          i32.const 0
                          local.set $dn1
                          local.get $dn1
                          local.set $dm
                          local.get $dm
                          local.set $b
                        else
                          i32.const 6
                          i32.const 2
                          call $logI32
                          i32.const 0
                          local.set $dk1
                          local.get $dk1
                          local.set $dj
                          local.get $dj
                          local.set $b
                        end
                      else
                        i32.const 6
                        i32.const 2
                        call $logI32
                        i32.const 0
                        local.set $df1
                        local.get $df1
                        local.set $de
                        local.get $de
                        local.set $b
                      end
                    else
                      i32.const 6
                      i32.const 2
                      call $logI32
                      i32.const 0
                      local.set $cw1
                      local.get $cw1
                      local.set $cv
                      local.get $cv
                      local.set $b
                    end
                    br $bu
                  end
                  local.get $tmp62
                  i32.const 0
                  i32.eq
                  if
                    local.get $tmp71
                    i32.const 0
                    i32.eq
                    if
                      local.get $tmp8
                      i32.const 0
                      i32.eq
                      if
                        i32.const 4
                        i32.const 2
                        call $logI32
                        i32.const 0
                        local.set $cr1
                        local.get $cr1
                        local.set $cq
                        local.get $cq
                        local.set $b
                      else
                        i32.const 6
                        i32.const 2
                        call $logI32
                        i32.const 0
                        local.set $co1
                        local.get $co1
                        local.set $cn
                        local.get $cn
                        local.set $b
                      end
                    else
                      i32.const 6
                      i32.const 2
                      call $logI32
                      i32.const 0
                      local.set $cj1
                      local.get $cj1
                      local.set $ci
                      local.get $ci
                      local.set $b
                    end
                  else
                    i32.const 6
                    i32.const 2
                    call $logI32
                    i32.const 0
                    local.set $ce1
                    local.get $ce1
                    local.set $cd
                    local.get $cd
                    local.set $b
                  end
                  br $bu
                end
                i32.const 6
                i32.const 2
                call $logI32
                i32.const 0
                local.set $bw1
                local.get $bw1
                local.set $bv
                local.get $bv
                local.set $b
              end
              br $d
            end
            local.get $tmp4
            i32.const 4
            i32.add
            i32.load
            local.set $af
            local.get $af
            local.set $tmp51
            local.get $tmp4
            i32.const 8
            i32.add
            i32.load
            local.set $ag
            local.get $ag
            local.set $tmp61
            local.get $tmp4
            i32.const 12
            i32.add
            i32.load
            local.set $ah
            local.get $ah
            local.set $tmp7
            block $aj
              block $am
                block $az
                  block $Match_tmp51
                    local.get $tmp51
                    i32.const 0
                    i32.eq
                    i32.const 1
                    i32.mul
                    local.get $tmp51
                    i32.const 1
                    i32.eq
                    i32.const 2
                    i32.mul
                    i32.add
                    br_table 2 1 0
                  end
                  local.get $tmp61
                  i32.const 2
                  i32.eq
                  if
                    local.get $tmp7
                    i32.const 3
                    i32.eq
                    if
                      i32.const 3
                      i32.const 2
                      call $logI32
                      i32.const 0
                      local.set $bn1
                      local.get $bn1
                      local.set $bm
                      local.get $bm
                      local.set $b
                    else
                      i32.const 6
                      i32.const 2
                      call $logI32
                      i32.const 0
                      local.set $bk1
                      local.get $bk1
                      local.set $bj
                      local.get $bj
                      local.set $b
                    end
                  else
                    i32.const 6
                    i32.const 2
                    call $logI32
                    i32.const 0
                    local.set $bf1
                    local.get $bf1
                    local.set $be
                    local.get $be
                    local.set $b
                  end
                  br $aj
                end
                local.get $tmp61
                i32.const 0
                i32.eq
                if
                  local.get $tmp7
                  i32.const 0
                  i32.eq
                  if
                    i32.const 2
                    i32.const 2
                    call $logI32
                    i32.const 0
                    local.set $ay1
                    local.get $ay1
                    local.set $ax
                    local.get $ax
                    local.set $b
                  else
                    i32.const 6
                    i32.const 2
                    call $logI32
                    i32.const 0
                    local.set $av1
                    local.get $av1
                    local.set $au
                    local.get $au
                    local.set $b
                  end
                else
                  i32.const 6
                  i32.const 2
                  call $logI32
                  i32.const 0
                  local.set $aq1
                  local.get $aq1
                  local.set $ap
                  local.get $ap
                  local.set $b
                end
                br $aj
              end
              i32.const 6
              i32.const 2
              call $logI32
              i32.const 0
              local.set $al1
              local.get $al1
              local.set $ak
              local.get $ak
              local.set $b
            end
            br $d
          end
          local.get $tmp4
          i32.const 4
          i32.add
          i32.load
          local.set $h
          local.get $h
          local.set $tmp5
          local.get $tmp4
          i32.const 8
          i32.add
          i32.load
          local.set $i
          local.get $i
          local.set $tmp6
          block $k
            block $n
              block $v
                block $Match_tmp5
                  local.get $tmp5
                  i32.const 0
                  i32.eq
                  i32.const 1
                  i32.mul
                  local.get $tmp5
                  i32.const 1
                  i32.eq
                  i32.const 2
                  i32.mul
                  i32.add
                  br_table 2 1 0
                end
                local.get $tmp6
                i32.const 2
                i32.eq
                if
                  i32.const 1
                  i32.const 2
                  call $logI32
                  i32.const 0
                  local.set $ad1
                  local.get $ad1
                  local.set $ac
                  local.get $ac
                  local.set $b
                else
                  i32.const 6
                  i32.const 2
                  call $logI32
                  i32.const 0
                  local.set $z1
                  local.get $z1
                  local.set $y
                  local.get $y
                  local.set $b
                end
                br $k
              end
              local.get $tmp6
              i32.const 0
              i32.eq
              if
                i32.const 0
                i32.const 2
                call $logI32
                i32.const 0
                local.set $u1
                local.get $u1
                local.set $t
                local.get $t
                local.set $b
              else
                i32.const 6
                i32.const 2
                call $logI32
                i32.const 0
                local.set $r1
                local.get $r1
                local.set $q
                local.get $q
                local.set $b
              end
              br $k
            end
            i32.const 6
            i32.const 2
            call $logI32
            i32.const 0
            local.set $m1
            local.get $m1
            local.set $l
            local.get $l
            local.set $b
          end
          br $d
        end
        i32.const 6
        i32.const 2
        call $logI32
        i32.const 0
        local.set $f1
        local.get $f1
        local.set $e
        local.get $e
        local.set $b
      end
    end
    local.get $b
    local.set $result1
  )
  (export "main_8" (func $main_8))
  (func $main_8 (local $a i32)(local $z1 i32)(local $o1 i32)(local $x1 i32)(local $h i32)(local $w i32)(local $tmp9 i32)(local $k i32)(local $e i32)(local $l1 i32)(local $n i32)(local $res1 i32)(local $u1 i32)(local $t i32)(local $f1 i32)(local $q i32)(local $b i32)(local $y1 i32)(local $r1 i32)
    block $entry
      global.get 0
      local.set $a
      global.get 0
      i32.const 0
      i32.store
      global.get 0
      i32.const 8
      i32.add
      global.set 0
      local.get $a
      i32.const 4
      i32.add
      i32.const 1
      i32.store
      local.get $a
      local.set $x1
      block $d
        block $g
          block $s
            block $v
              block $Match_x1
                local.get $x1
                i32.load
                i32.const 0
                i32.eq
                i32.const 1
                i32.mul
                local.get $x1
                i32.load
                i32.const 1
                i32.eq
                i32.const 2
                i32.mul
                i32.add
                local.get $x1
                i32.load
                i32.const 2
                i32.eq
                i32.const 3
                i32.mul
                i32.add
                br_table 3 2 1 0
              end
              i32.const 30
              i32.const 2
              call $logI32
              i32.const 0
              local.set $y1
              local.get $y1
              local.set $w
              local.get $w
              local.set $b
              br $d
            end
            i32.const 20
            i32.const 2
            call $logI32
            i32.const 0
            local.set $u1
            local.get $u1
            local.set $t
            local.get $t
            local.set $b
            br $d
          end
          local.get $x1
          local.set $tmp9
          local.get $tmp9
          i32.const 4
          i32.add
          i32.load
          local.set $h
          local.get $h
          local.set $x1
          block $j
            block $m
              block $p
                block $Match_x1
                  local.get $x1
                  i32.const 0
                  i32.eq
                  i32.const 1
                  i32.mul
                  local.get $x1
                  i32.const 1
                  i32.eq
                  i32.const 2
                  i32.mul
                  i32.add
                  br_table 2 1 0
                end
                i32.const 1
                i32.const 2
                call $logI32
                i32.const 0
                local.set $r1
                i32.const 3
                local.set $q
                local.get $q
                local.set $b
                br $j
              end
              i32.const 0
              i32.const 2
              call $logI32
              i32.const 0
              local.set $o1
              local.get $o1
              local.set $n
              local.get $n
              local.set $b
              br $j
            end
            i32.const 40
            i32.const 2
            call $logI32
            i32.const 0
            local.set $l1
            local.get $l1
            local.set $k
            local.get $k
            local.set $b
          end
          br $d
        end
        i32.const 40
        i32.const 2
        call $logI32
        i32.const 0
        local.set $f1
        local.get $f1
        local.set $e
        local.get $e
        local.set $b
      end
    end
    local.get $b
    local.set $res1
    local.get $res1
    i32.const 0
    call $logI32
    i32.const 0
    local.set $z1
  )
  (export "main" (func $main))
  (func $main 
    call $main_0
    call $main_1
    call $main_2
    call $main_3
    call $main_4
    call $main_5
    call $main_6
    call $main_7
    call $main_8
  )
)